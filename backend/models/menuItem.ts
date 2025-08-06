import { pgTable, serial, text, integer, decimal } from "drizzle-orm/pg-core";
import { menus } from "@/models/menu.ts";

export const menuItems = pgTable("menu_items", {
  id: serial("id").primaryKey(),
  menuId: integer("menu_id").references(() => menus.id).notNull(),
  name: text("name").notNull(),
  price: decimal("price", { precision: 10, scale: 2 }),
  description: text("description"),
  category: text("category"),
  available: integer("available").default(1), // 1 = available, 0 = not available
});