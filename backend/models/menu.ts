import { pgTable, serial, timestamp, integer } from "drizzle-orm/pg-core";
import { restaurants } from "@/models/restaurant.ts";

export const menus = pgTable("menus", {
  id: serial("id").primaryKey(),
  restaurantId: integer("restaurant_id").references(() => restaurants.id).notNull(),
  date: timestamp("date").defaultNow().notNull(),
  createdAt: timestamp("created_at").defaultNow(),
});