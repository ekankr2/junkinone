import { ScrapedMenuItem } from "@/lib/scraper/base-scraper";
import * as cheerio from 'cheerio';
import puppeteer from 'puppeteer';

export class BHCScraper {
  private readonly url = 'https://www.bhc.co.kr/menu/chicken.asp';

  async scrape(): Promise<ScrapedMenuItem[]> {
    try {
      const browser = await puppeteer.launch({
        headless: true,
        args: ['--no-sandbox', '--disable-setuid-sandbox']
      });

      const page = await browser.newPage();
      await page.setUserAgent('Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36');
      
      await page.goto(this.url, { 
        waitUntil: 'networkidle2',
        timeout: 30000 
      });

      await page.waitForSelector('.cokepopList', { timeout: 10000 });

      const html = await page.content();
      await browser.close();

      return this.parseHTML(html);
    } catch (error) {
      console.error('BHC scraping failed:', error);
      throw error;
    }
  }

  private parseHTML(html: string): ScrapedMenuItem[] {
    const $ = cheerio.load(html);
    const items: ScrapedMenuItem[] = [];
    const distinctNames = new Set<string>();

    $('.cokepopList li').each((_, element) => {
      const $item = $(element);
      
      // Check if item has NEW tag
      const hasNewTag = $item.find("img[alt='NEW']").length > 0;
      if (!hasNewTag) return;
      
      const nameElement = $item.find('p');
      let fullName = nameElement.text().trim();
      if (!fullName) return;

      // Clean up name by removing NEW tag text
      fullName = fullName.replace(/\s*NEW\s*/g, '').trim();

      // Extract base name (everything before the first space)
      // e.g. "콰삭톡 한마리" -> "콰삭톡", "콰삭킹 순살" -> "콰삭킹"
      const baseName = fullName.split(' ')[0];
      
      // Skip if we already have this base name
      if (distinctNames.has(baseName)) return;
      distinctNames.add(baseName);

      items.push({
        name: baseName,
        price: undefined,
        description: undefined,
        category: 'FRIED'
      });
    });

    return items;
  }
}