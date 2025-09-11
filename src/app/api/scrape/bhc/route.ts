import { NextResponse } from 'next/server';
import { BHCScraper } from '@/lib/scraper/brands/bhc-scraper';
import { Brand, Menu } from '@/db/models';
import connectDB from '@/db';
import { Types } from 'mongoose';

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
    price: item.price ? parseFloat(item.price.replace(/[^0-9.]/g, '')) || undefined : undefined,
    description: item.description?.trim() || undefined,
    category: item.category?.trim() || undefined
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
    await connectDB();
    
    // Find BHC brand in database
    const brand = await Brand.findOne({ name: 'BHC' });
    if (!brand) {
      return NextResponse.json({ error: 'BHC brand not found in database' }, { status: 404 });
    }

    const scraper = new BHCScraper();
    const scrapedItems = await scraper.scrape();

    if (scrapedItems.length === 0) {
      return NextResponse.json({ error: 'No menu items found' }, { status: 404 });
    }

    // Get the latest menu for this brand
    const currentMenu = await Menu
      .findOne({ brandId: brand._id })
      .sort({ date: -1 });

    // Normalize scraped items for comparison
    const normalizedScrapedItems = scrapedItems.map(normalizeItem);

    // Check if items have changed
    let itemsChanged = true;
    if (currentMenu && currentMenu.items) {
      itemsChanged = 
        normalizedScrapedItems.length !== currentMenu.items.length ||
        !normalizedScrapedItems.every((scrapedItem: any) => {
          const existingItem = currentMenu.items.find((item: any) => item.name === scrapedItem.name);
          return existingItem && itemsAreEqual(scrapedItem, existingItem);
        });
    }

    if (!itemsChanged && currentMenu) {
      // Items haven't changed, just update the menu date to show it was checked
      currentMenu.date = new Date();
      await currentMenu.save();

      return NextResponse.json({
        message: `No changes detected. Updated check time for existing menu`,
        menuId: currentMenu._id,
        itemCount: currentMenu.items.length,
        changed: false
      });
    }

    // Items have changed, create new menu
    const newMenu = new Menu({
      brandId: brand._id,
      date: new Date(),
      items: normalizedScrapedItems
    });

    await newMenu.save();

    return NextResponse.json({
      message: `Successfully scraped ${scrapedItems.length} BHC menu items (items changed)`,
      menuId: newMenu._id,
      itemCount: scrapedItems.length,
      changed: true,
      previousMenuId: currentMenu?._id
    });
  } catch (error) {
    console.error('BHC scraping failed:', error);
    return NextResponse.json({ error: 'BHC scraping failed' }, { status: 500 });
  }
}
