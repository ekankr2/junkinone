import { NextResponse } from 'next/server';
import { Brand } from '@/db/models';
import connectDB from '@/db';

export async function GET() {
  try {
    await connectDB();
    const brands = await Brand.find({});

    return NextResponse.json(brands);
  } catch (error) {
    console.error(error);
    return NextResponse.json({ error: 'Failed to fetch brands' }, { status: 500 });
  }
}
