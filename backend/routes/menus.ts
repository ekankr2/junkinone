import { Hono, Context } from 'hono'
import { db } from '@/lib/database.ts'
import { Brands, Menus, MenuItems } from '@/models/index.ts'
import { eq, sql } from 'drizzle-orm'

const menus = new Hono()

// Get latest menus for all brands
menus.get('/', async (c: Context) => {
  try {
    const latestMenus = await db
      .select({
        menuId: Menus.id,
        brandId: Brands.id,
        brandName: Brands.name,
        brandWebsite: Brands.website,
        menuDate: Menus.date,
        itemName: MenuItems.name,
        price: MenuItems.price,
        category: MenuItems.category,
        description: MenuItems.description,
        available: MenuItems.available
      })
      .from(Menus)
      .innerJoin(Brands, eq(Menus.brand_id, Brands.id))
      .leftJoin(MenuItems, eq(MenuItems.menu_id, Menus.id))
      .where(
        sql`${Menus.id} IN (
          SELECT DISTINCT ON (brand_id) id 
          FROM menus 
          ORDER BY brand_id, date DESC
        )`
      )
    
    return c.json(latestMenus)
  } catch (_error) {
    return c.json({ error: 'Failed to fetch latest menus' }, 500)
  }
})

export default menus