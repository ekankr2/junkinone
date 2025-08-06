import { pgTable, serial, text, timestamp } from "drizzle-orm/pg-core";

export const restaurants = pgTable("restaurants", {
  id: serial("id").primaryKey(),
  name: text("name").notNull(),
  website: text("website").notNull(),
  scrapingConfig: text("scraping_config"), // JSON config for scraping
  createdAt: timestamp("created_at").defaultNow(),
});