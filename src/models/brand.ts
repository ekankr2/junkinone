import { pgTable, serial, text, timestamp } from "drizzle-orm/pg-core";

export const Brands = pgTable("brands", {
  id: serial("id").primaryKey(),
  name: text("name").notNull(),
  website: text("website").notNull(),
  scraping_config: text("scraping_config"), // JSON config for scraping
  created_at: timestamp("created_at").defaultNow(),
});