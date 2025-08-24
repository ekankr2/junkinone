import { NextResponse } from 'next/server';
import { Menus, MenuItems } from '@/db/models';
import { eq, notInArray, sql } from 'drizzle-orm';
import db from '@/db';

export async function DELETE() {
  try {
    // Get the latest menu ID for each brand
    const latestMenuIds = await db
      .select({
        id: sql<number>`DISTINCT ON (brand_id) id`.as('id')
      })
      .from(Menus)
      .orderBy(sql`brand_id, date DESC`);

    const latestIds = latestMenuIds.map(menu => menu.id);

    if (latestIds.length === 0) {
      return NextResponse.json({ message: 'No menus to clean up' });
    }

    // Delete old menu records (menu items will be automatically deleted via cascade)
    const deletedMenusResult = await db
      .delete(Menus)
      .where(notInArray(Menus.id, latestIds))
      .returning({ id: Menus.id });

    return NextResponse.json({
      message: `Cleaned up ${deletedMenusResult.length} old menus (menu items automatically deleted via cascade)`,
      deletedMenus: deletedMenusResult.length,
      keptLatestMenus: latestIds
    });
  } catch (error) {
    console.error('Menu cleanup failed:', error);
    return NextResponse.json({ error: 'Menu cleanup failed' }, { status: 500 });
  }
}