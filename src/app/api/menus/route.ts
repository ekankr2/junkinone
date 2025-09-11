import { NextResponse } from 'next/server';
import { Brand, Menu } from '@/db/models';
import connectDB from '@/db';

export async function GET() {
  try {
    await connectDB();
    
    const latestMenus = await Menu.aggregate([
      {
        $sort: { brandId: 1, date: -1 }
      },
      {
        $group: {
          _id: '$brandId',
          latestMenu: { $first: '$$ROOT' }
        }
      },
      {
        $replaceRoot: { newRoot: '$latestMenu' }
      },
      {
        $lookup: {
          from: 'brands',
          localField: 'brandId',
          foreignField: '_id',
          as: 'brand'
        }
      },
      {
        $unwind: '$brand'
      },
      {
        $project: {
          _id: 1,
          brandId: 1,
          date: 1,
          items: 1,
          createdAt: 1,
          updatedAt: 1,
          'brand.name': 1,
          'brand.website': 1
        }
      }
    ]);
    
    return NextResponse.json(latestMenus);
  } catch (error) {
    return NextResponse.json({ error: 'Failed to fetch latest menus' }, { status: 500 });
  }
}