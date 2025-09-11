import { NextResponse } from 'next/server';
import { Brand } from '@/db/models';
import connectDB from '@/db';

export async function GET() {
  try {
    await connectDB();
    const brandsData = await Brand.find({}).sort({ createdAt: -1 });
    return NextResponse.json(brandsData);
  } catch (error) {
    return NextResponse.json({ error: 'Failed to fetch brands' }, { status: 500 });
  }
}