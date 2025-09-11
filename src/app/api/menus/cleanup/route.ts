import { NextResponse } from 'next/server';
import { Menu } from '@/db/models';
import connectDB from '@/db';

export async function DELETE() {
  try {
    await connectDB();
    
    // Get the latest menu ID for each brand using aggregation
    const latestMenus = await Menu.aggregate([
      {
        $sort: { brandId: 1, date: -1 }
      },
      {
        $group: {
          _id: '$brandId',
          latestMenuId: { $first: '$_id' }
        }
      }
    ]);

    const latestMenuIds = latestMenus.map(menu => menu.latestMenuId);

    if (latestMenuIds.length === 0) {
      return NextResponse.json({ message: 'No menus to clean up' });
    }

    // Delete old menu records (items are embedded, so they'll be deleted with the menu)
    const deleteResult = await Menu.deleteMany({
      _id: { $nin: latestMenuIds }
    });

    return NextResponse.json({
      message: `Cleaned up ${deleteResult.deletedCount} old menus`,
      deletedMenus: deleteResult.deletedCount,
      keptLatestMenus: latestMenuIds.length
    });
  } catch (error) {
    console.error('Menu cleanup failed:', error);
    return NextResponse.json({ error: 'Menu cleanup failed' }, { status: 500 });
  }
}