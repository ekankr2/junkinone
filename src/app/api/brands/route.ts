import { NextRequest, NextResponse } from 'next/server';
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

export async function POST(request: NextRequest) {
  try {
    await connectDB();
    const body = await request.json();
    
    const { name, website } = body;
    
    if (!name || !website) {
      return NextResponse.json({ error: 'Name and website are required' }, { status: 400 });
    }

    // Check if brand already exists
    const existingBrand = await Brand.findOne({ name });
    if (existingBrand) {
      return NextResponse.json({ error: 'Brand already exists' }, { status: 409 });
    }

    const brand = await Brand.create({ name, website });
    
    return NextResponse.json(brand, { status: 201 });
  } catch (error) {
    return NextResponse.json({ error: 'Failed to create brand' }, { status: 500 });
  }
}