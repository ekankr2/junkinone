import * as cheerio from 'cheerio';
import puppeteer from 'puppeteer'

export interface ScrapedMenuItem {
  name: string;
  price?: string;
  description?: string;
  category?: string;
  available?: boolean;
}

export interface ScrapeConfig {
  url: string;
  selectors: {
    menuItems: string;
    name: string;
    price?: string;
    description?: string;
    category?: string;
  };
  useHeadless?: boolean;
  waitForSelector?: string;
  pagination?: {
    selector: string;
    maxPages?: number;
  };
}

export abstract class BaseScraper {
  protected config: ScrapeConfig;

  constructor(config: ScrapeConfig) {
    this.config = config;
  }

  async scrape(): Promise<ScrapedMenuItem[]> {
    try {
      if (this.config.useHeadless) {
        return await this.scrapeWithPuppeteer();
      } else {
        return await this.scrapeWithFetch();
      }
    } catch (error) {
      console.error('Scraping failed:', error);
      throw error;
    }
  }

  private async scrapeWithFetch(): Promise<ScrapedMenuItem[]> {
    const response = await fetch(this.config.url, {
      headers: {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36'
      }
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const html = await response.text();
    return this.parseHTML(html);
  }

  private async scrapeWithPuppeteer(): Promise<ScrapedMenuItem[]> {
    const browser = await puppeteer.launch({
      headless: true,
      args: ['--no-sandbox', '--disable-setuid-sandbox']
    });

    try {
      const page = await browser.newPage();
      await page.setUserAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36');
      
      await page.goto(this.config.url, { 
        waitUntil: 'networkidle2',
        timeout: 30000 
      });

      if (this.config.waitForSelector) {
        await page.waitForSelector(this.config.waitForSelector, { timeout: 10000 });
      }

      const html = await page.content();
      return this.parseHTML(html);
    } finally {
      await browser.close();
    }
  }

  private parseHTML(html: string): ScrapedMenuItem[] {
    const $ = cheerio.load(html);
    const items: ScrapedMenuItem[] = [];

    $(this.config.selectors.menuItems).each((index, element) => {
      const $item = $(element);
      
      const name = $item.find(this.config.selectors.name).text().trim();
      if (!name) return;

      const price = this.config.selectors.price 
        ? $item.find(this.config.selectors.price).text().trim()
        : undefined;

      const description = this.config.selectors.description 
        ? $item.find(this.config.selectors.description).text().trim()
        : undefined;

      const category = this.config.selectors.category 
        ? $item.find(this.config.selectors.category).text().trim()
        : undefined;

      items.push({
        name,
        price,
        description,
        category,
        available: true
      });
    });

    return items;
  }

  protected cleanPrice(priceText: string): string {
    return priceText.replace(/[^\d.,]/g, '').replace(',', '.');
  }

  protected formatPrice(priceText: string): string | undefined {
    const cleaned = this.cleanPrice(priceText);
    return cleaned ? parseFloat(cleaned).toFixed(2) : undefined;
  }
}