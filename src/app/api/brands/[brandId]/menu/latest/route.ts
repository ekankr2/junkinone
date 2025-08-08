import { NextRequest, NextResponse } from 'next/server';
import { db } from '@/lib/database';
import { Brands, Menus, MenuItems } from '@/lib/models';
import { eq, desc } from 'drizzle-orm';

export async function GET(
  request: NextRequest,
  { params }: { params: Promise<{ brandId: string }> }
) {
  try {
    const { brandId: brandIdParam } = await params;
    const brandId = parseInt(brandIdParam);
    
    const latestMenu = await db
      .select()
      .from(Menus)
      .where(eq(Menus.brand_id, brandId))
      .orderBy(desc(Menus.date))
      .limit(1);
    
    if (latestMenu.length === 0) {
      return NextResponse.json({ error: 'No menu found for this brand' }, { status: 404 });
    }

    const menuItems = await db
      .select()
      .from(MenuItems)
      .where(eq(MenuItems.menu_id, latestMenu[0].id));
    
    return NextResponse.json({
      menu: latestMenu[0],
      items: menuItems
    });
  } catch (error) {
    return NextResponse.json({ error: 'Failed to fetch menu' }, { status: 500 });
  }
}