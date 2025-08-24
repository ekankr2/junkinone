import { NextResponse } from 'next/server';
import { Brands, Menus, MenuItems } from '@/db/models';
import { BHCScraper } from '@/lib/scraper/brands/bhc-scraper';
import { eq } from 'drizzle-orm';
import db from "@/db";

export async function POST() {
  try {
    // Test simple query first
    const brands = await db.select().from(Brands).where(eq(Brands.name, 'BHC'));
    if (brands.length === 0) {
      return NextResponse.json({ error: 'BHC brand not found in database' }, { status: 404 });
    }

    return NextResponse.json({ 
      message: `Found BHC brand with id: ${brands[0].id}`,
      brand: brands[0]
    });
  } catch (error) {
    console.error('BHC scraping failed:', error);
    return NextResponse.json({ error: 'BHC scraping failed' }, { status: 500 });
  }
}