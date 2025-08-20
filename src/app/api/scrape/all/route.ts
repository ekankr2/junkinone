import { NextResponse } from 'next/server';

export async function POST() {
  try {
    const scraperEndpoints = [
      '/api/scrape/bhc',
      // Add more scraper endpoints as you implement them
      // '/api/scrape/kfc',
    ];

    const results = [];
    
    for (const endpoint of scraperEndpoints) {
      try {
        const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL || 'http://localhost:3000'}${endpoint}`, {
          method: 'POST',
        });
        
        const result = await response.json();
        results.push({
          endpoint,
          success: response.ok,
          result
        });
      } catch (error) {
        results.push({
          endpoint,
          success: false,
          error: error instanceof Error ? error.message : 'Unknown error'
        });
      }
    }

    const successCount = results.filter(r => r.success).length;
    const totalCount = results.length;

    return NextResponse.json({
      message: `Scraping completed: ${successCount}/${totalCount} successful`,
      results
    });
  } catch (error) {
    console.error('Batch scraping failed:', error);
    return NextResponse.json({ error: 'Batch scraping failed' }, { status: 500 });
  }
}