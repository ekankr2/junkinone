import { Hono, Context } from 'hono'
import { db } from '@/lib/database.ts'
import { Brands, Menus, MenuItems } from '@/models/index.ts'
import { eq, sql } from 'drizzle-orm'

const menus = new Hono()

menus.get('/today', async (c: Context) => {
  try {
    const today = new Date()
    today.setHours(0, 0, 0, 0) // Start of day
    
    const todayMenus = await db
      .select({
        menuId: Menus.id,
        brandName: Brands.name,
        date: Menus.date,
        itemName: MenuItems.name,
        price: MenuItems.price,
        category: MenuItems.category,
        description: MenuItems.description
      })
      .from(Menus)
      .leftJoin(Brands, eq(Menus.brand_id, Brands.id))
      .leftJoin(MenuItems, eq(MenuItems.menu_id, Menus.id))
      .where(sql`${Menus.date}::date = ${today.toISOString().split('T')[0]}`)
    
    return c.json(todayMenus)
  } catch (_error) {
    return c.json({ error: 'Failed to fetch today\'s menus' }, 500)
  }
})

export default menus