import { NextResponse } from 'next/server';
import { Brands } from '@/db/models';
import db from '@/db';

export async function GET() {
  try {
    const latestMenus = await db.select().from(Brands);

    return NextResponse.json(latestMenus);
  } catch (error) {
    console.error(error);
    return NextResponse.json({ error: 'Failed to fetch latest menus' }, { status: 500 });
  }
}
