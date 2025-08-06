import { pgTable, serial, timestamp, integer, text, decimal } from "drizzle-orm/pg-core";
import { Brands } from "@/models/brand.ts";

export const Menus = pgTable("menus", {
  id: serial("id").primaryKey(),
  brand_id: integer("brand_id").references(() => Brands.id).notNull(),
  date: timestamp("date").defaultNow().notNull(),
  created_at: timestamp("created_at").defaultNow(),
});

export const MenuItems = pgTable("menu_items", {
  id: serial("id").primaryKey(),
  menu_id: integer("menu_id").references(() => Menus.id).notNull(),
  name: text("name").notNull(),
  price: decimal("price", { precision: 10, scale: 2 }),
  description: text("description"),
  category: text("category"),
  available: integer("available").default(1), // 1 = available, 0 = not available
});