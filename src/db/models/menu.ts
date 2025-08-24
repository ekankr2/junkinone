import { pgTable, serial, timestamp, integer, text, decimal } from "drizzle-orm/pg-core";
import { Brands } from "./brand";

export const Menus = pgTable("menus", {
  id: serial("id").primaryKey(),
  brand_id: integer("brand_id").references(() => Brands.id, { onDelete: "cascade" }).notNull(),
  date: timestamp("date").defaultNow().notNull(),
  created_at: timestamp("created_at").defaultNow(),
});

export const MenuItems = pgTable("menu_items", {
  id: serial("id").primaryKey(),
  menu_id: integer("menu_id").references(() => Menus.id, { onDelete: "cascade" }).notNull(),
  name: text("name").notNull(),
  price: decimal("price", { precision: 10, scale: 2 }),
  description: text("description"),
  category: text("category"),
});