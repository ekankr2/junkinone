import { NextResponse } from 'next/server';
import { Brands, Menus, MenuItems } from '@/db/models';
import { eq, sql } from 'drizzle-orm';
import db from "@/db";

export async function GET() {
  try {
    const latestMenus = await db
      .select({
        menuId: Menus.id,
        brandId: Brands.id,
        brandName: Brands.name,
        brandWebsite: Brands.website,
        menuDate: Menus.date,
        itemName: MenuItems.name,
        price: MenuItems.price,
        category: MenuItems.category,
        description: MenuItems.description,
        available: MenuItems.available
      })
      .from(Menus)
      .innerJoin(Brands, eq(Menus.brand_id, Brands.id))
      .leftJoin(MenuItems, eq(MenuItems.menu_id, Menus.id))
      .where(
        sql`${Menus.id} IN (
          SELECT DISTINCT ON (brand_id) id 
          FROM menus 
          ORDER BY brand_id, date DESC
        )`
      );
    
    return NextResponse.json(latestMenus);
  } catch (error) {
    return NextResponse.json({ error: 'Failed to fetch latest menus' }, { status: 500 });
  }
}