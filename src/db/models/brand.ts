import { pgTable, serial, text, timestamp } from "drizzle-orm/pg-core";

export const Brands = pgTable("brands", {
  id: serial("id").primaryKey(),
  name: text("name").notNull(),
  website: text("website").notNull(),
  created_at: timestamp("created_at").defaultNow(),
});