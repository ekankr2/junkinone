import { Brand, Menu } from '@/db/models';
import { BHCScraper } from './brands/bhc-scraper';
import connectDB from '@/db';
import { Types } from 'mongoose';

export class ScraperService {
  private scrapers: Map<string, any> = new Map();

  constructor() {
    this.scrapers.set('bhc', BHCScraper);
  }

  async scrapeAllBrands(): Promise<void> {
    await connectDB();
    const brands = await Brand.find({});
    
    for (const brand of brands) {
      try {
        await this.scrapeBrand(brand._id.toString(), brand.name.toLowerCase());
      } catch (error) {
        console.error(`Failed to scrape ${brand.name}:`, error);
      }
    }
  }

  async scrapeBrand(brandId: string, brandKey: string): Promise<void> {
    const ScraperClass = this.scrapers.get(brandKey);
    if (!ScraperClass) {
      console.warn(`No scraper found for brand: ${brandKey}`);
      return;
    }

    const scraper = new ScraperClass();
    const menuItems = await scraper.scrape();

    if (menuItems.length === 0) {
      console.warn(`No menu items found for brand: ${brandKey}`);
      return;
    }

    const normalizedItems = menuItems.map((item: any) => ({
      name: item.name,
      price: item.price ? parseFloat(item.price.replace(/[^0-9.]/g, '')) || undefined : undefined,
      description: item.description || undefined,
      category: item.category || undefined,
    }));

    const menu = await Menu.create({
      brandId: new Types.ObjectId(brandId),
      date: new Date(),
      items: normalizedItems
    });
    
    console.log(`Successfully scraped ${menuItems.length} items for ${brandKey}`);
  }

  async addBrand(name: string, website: string): Promise<string> {
    await connectDB();
    const brand = await Brand.create({ name, website });
    return brand._id.toString();
  }
}