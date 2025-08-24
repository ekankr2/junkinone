import { NextResponse } from 'next/server';
import { BHCScraper } from '@/lib/scraper/brands/bhc-scraper';
import { eq, sql } from 'drizzle-orm';
import db from '@/db';
import { Brands, MenuItems, Menus } from '@/db/models';

interface ScrapedItem {
  name: string;
  price?: string;
  description?: string;
  category?: string;
  available?: boolean;
}

function normalizeItem(item: ScrapedItem) {
  return {
    name: item.name.trim(),
    price: item.price?.trim() || null,
    description: item.description?.trim() || null,
    category: item.category?.trim() || null
  };
}

function itemsAreEqual(scraped: ReturnType<typeof normalizeItem>, existing: any) {
  return (
    scraped.name === existing.name &&
    scraped.price === existing.price &&
    scraped.description === existing.description &&
    scraped.category === existing.category
  );
}

export async function POST() {
  try {
    // Find BHC brand in database
    const brands = await db.select().from(Brands).where(eq(Brands.name, 'BHC'));
    if (brands.length === 0) {
      return NextResponse.json({ error: 'BHC brand not found in database' }, { status: 404 });
    }

    const brand = brands[0];
    const scraper = new BHCScraper();
    const scrapedItems = await scraper.scrape();

    if (scrapedItems.length === 0) {
      return NextResponse.json({ error: 'No menu items found' }, { status: 404 });
    }

    // Get the latest menu for this brand
    const latestMenus = await db
      .select()
      .from(Menus)
      .where(eq(Menus.brand_id, brand.id))
      .orderBy(sql`${Menus.date} DESC`)
      .limit(1);

    let currentMenu = latestMenus[0];
    let existingItems: any[] = [];

    if (currentMenu) {
      // Get existing menu items
      existingItems = await db
        .select()
        .from(MenuItems)
        .where(eq(MenuItems.menu_id, currentMenu.id));
    }

    // Normalize scraped items for comparison
    const normalizedScrapedItems = scrapedItems.map(normalizeItem);

    // Check if items have changed
    const itemsChanged = 
      normalizedScrapedItems.length !== existingItems.length ||
      !normalizedScrapedItems.every((scrapedItem, index) => {
        const existingItem = existingItems.find(item => item.name === scrapedItem.name);
        return existingItem && itemsAreEqual(scrapedItem, existingItem);
      });

    if (!itemsChanged && currentMenu) {
      // Items haven't changed, just update the menu date to show it was checked
      await db
        .update(Menus)
        .set({ date: new Date() })
        .where(eq(Menus.id, currentMenu.id));

      return NextResponse.json({
        message: `No changes detected. Updated check time for existing menu`,
        menuId: currentMenu.id,
        itemCount: existingItems.length,
        changed: false
      });
    }

    // Items have changed, create new menu
    const [newMenu] = await db
      .insert(Menus)
      .values({
        brand_id: brand.id,
        date: new Date(),
      })
      .returning();

    // Insert new menu items
    const menuItemsData = normalizedScrapedItems.map(item => ({
      menu_id: newMenu.id,
      name: item.name,
      price: item.price,
      description: item.description,
      category: item.category,
    }));

    await db.insert(MenuItems).values(menuItemsData);

    return NextResponse.json({
      message: `Successfully scraped ${scrapedItems.length} BHC menu items (items changed)`,
      menuId: newMenu.id,
      itemCount: scrapedItems.length,
      changed: true,
      previousMenuId: currentMenu?.id
    });
  } catch (error) {
    console.error('BHC scraping failed:', error);
    return NextResponse.json({ error: 'BHC scraping failed' }, { status: 500 });
  }
}
