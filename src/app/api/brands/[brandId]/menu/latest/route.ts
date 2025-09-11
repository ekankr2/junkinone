import { NextRequest, NextResponse } from 'next/server';
import { Menu } from '@/db/models';
import connectDB from '@/db';
import { Types } from 'mongoose';

export async function GET(
  request: NextRequest,
  { params }: { params: Promise<{ brandId: string }> }
) {
  try {
    await connectDB();
    const { brandId: brandIdParam } = await params;
    
    if (!Types.ObjectId.isValid(brandIdParam)) {
      return NextResponse.json({ error: 'Invalid brand ID' }, { status: 400 });
    }
    
    const latestMenu = await Menu
      .findOne({ brandId: new Types.ObjectId(brandIdParam) })
      .sort({ date: -1 })
      .populate('brandId', 'name website');
    
    if (!latestMenu) {
      return NextResponse.json({ error: 'No menu found for this brand' }, { status: 404 });
    }
    
    return NextResponse.json(latestMenu);
  } catch (error) {
    return NextResponse.json({ error: 'Failed to fetch menu' }, { status: 500 });
  }
}