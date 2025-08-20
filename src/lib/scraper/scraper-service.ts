import { db } from '../database';
import { Brands, Menus, MenuItems } from '../models';
import { BHCScraper } from './brands/bhc-scraper';
import { eq } from 'drizzle-orm';

export class ScraperService {
  private scrapers: Map<string, any> = new Map();

  constructor() {
    this.scrapers.set('bhc', BHCScraper);
  }

  async scrapeAllBrands(): Promise<void> {
    const brands = await db.select().from(Brands);
    
    for (const brand of brands) {
      try {
        await this.scrapeBrand(brand.id, brand.name.toLowerCase());
      } catch (error) {
        console.error(`Failed to scrape ${brand.name}:`, error);
      }
    }
  }

  async scrapeBrand(brandId: number, brandKey: string): Promise<void> {
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

    const [menu] = await db
      .insert(Menus)
      .values({
        brand_id: brandId,
        date: new Date(),
      })
      .returning();

    const menuItemsData = menuItems.map((item: any) => ({
      menu_id: menu.id,
      name: item.name,
      price: item.price,
      description: item.description,
      category: item.category,
      available: item.available ? 1 : 0,
    }));

    await db.insert(MenuItems).values(menuItemsData);
    
    console.log(`Successfully scraped ${menuItems.length} items for ${brandKey}`);
  }

  async addBrand(name: string, website: string, scraperKey: string): Promise<number> {
    const [brand] = await db
      .insert(Brands)
      .values({
        name,
        website,
        scraping_config: JSON.stringify({ scraperKey })
      })
      .returning();

    return brand.id;
  }
}