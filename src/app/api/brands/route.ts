import { NextResponse } from 'next/server';
import { db } from '@/lib/database';
import { Brands } from '@/lib/models';

export async function GET() {
  try {
    const brandsData = await db.select().from(Brands);
    return NextResponse.json(brandsData);
  } catch (error) {
    return NextResponse.json({ error: 'Failed to fetch brands' }, { status: 500 });
  }
}