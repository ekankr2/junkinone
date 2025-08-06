import postgres from "postgres";
import { drizzle } from "drizzle-orm/postgres-js";
import * as schema from "@/models/index.ts";

const connectionString = Deno.env.get("DATABASE_URL") || "postgresql://localhost:5432/chicken_menus";

const client = postgres(connectionString);
export const db = drizzle(client, { schema });