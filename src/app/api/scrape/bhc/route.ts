import { NextResponse } from 'next/server';
import { Brands, Menus, MenuItems } from '@/db/models';
import { BHCScraper } from '@/lib/scraper/brands/bhc-scraper';
import { eq } from 'drizzle-orm';
import db from "@/db";

export async function POST() {
  try {
    // Find BHC brand in database
    const brands = await db.select().from(Brands).where(eq(Brands.name, 'BHC'));
    if (brands.length === 0) {
      return NextResponse.json({ error: 'BHC brand not found in database' }, { status: 404 });
    }

    const brand = brands[0];
    const scraper = new BHCScraper();
    const menuItems = await scraper.scrape();

    if (menuItems.length === 0) {
      return NextResponse.json({ error: 'No menu items found' }, { status: 404 });
    }

    // Create new menu entry
    const [menu] = await db
      .insert(Menus)
      .values({
        brand_id: brand.id,
        date: new Date(),
      })
      .returning();

    // Insert menu items
    const menuItemsData = menuItems.map((item: any) => ({
      menu_id: menu.id,
      name: item.name,
      price: item.price,
      description: item.description,
      category: item.category,
      available: item.available ? 1 : 0,
    }));

    await db.insert(MenuItems).values(menuItemsData);
    
    return NextResponse.json({ 
      message: `Successfully scraped ${menuItems.length} BHC menu items`,
      menuId: menu.id,
      itemCount: menuItems.length
    });
  } catch (error) {
    console.error('BHC scraping failed:', error);
    return NextResponse.json({ error: 'BHC scraping failed' }, { status: 500 });
  }
}