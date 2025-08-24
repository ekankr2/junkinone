import {BaseScraper, ScrapeConfig} from "@/lib/scraper/base-scraper";

export class BHCScraper extends BaseScraper {
  constructor() {
    const config: ScrapeConfig = {
      url: 'https://www.bhc.co.kr/menu/chicken.asp',
      useHeadless: true,
      waitForSelector: '.menu_list',
      selectors: {
        menuItems: '.menu_list li',
        name: '.menu_name',
        price: '.menu_price',
        description: '.menu_desc'
      }
    };
    super(config);
  }
}