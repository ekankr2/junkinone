import {NextResponse} from 'next/server';
import {Brands} from '@/db/models';
import db from "@/db";

export async function GET() {
  try {
    const brandsData = await db.select().from(Brands);
    return NextResponse.json(brandsData);
  } catch (error) {
    return NextResponse.json({ error: 'Failed to fetch brands' }, { status: 500 });
  }
}