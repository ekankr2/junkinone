import { Hono, Context } from 'hono'
import { db } from '@/lib/database.ts'
import { Brands, Menus, MenuItems } from '@/models/index.ts'
import { eq, desc } from 'drizzle-orm'

const brands = new Hono()

// Get all brands
brands.get('/', async (c: Context) => {
  try {
    const brandsData = await db.select().from(Brands)
    return c.json(brandsData)
  } catch (_error) {
    return c.json({ error: 'Failed to fetch brands' }, 500)
  }
})

// Get latest menu for a brand
brands.get('/:brandId/menu/latest', async (c: Context) => {
  try {
    const brandId = parseInt(c.req.param('brandId'))
    
    const latestMenu = await db
      .select()
      .from(Menus)
      .where(eq(Menus.brand_id, brandId))
      .orderBy(desc(Menus.date))
      .limit(1)
    
    if (latestMenu.length === 0) {
      return c.json({ error: 'No menu found for this brand' }, 404)
    }

    const menuItems = await db
      .select()
      .from(MenuItems)
      .where(eq(MenuItems.menu_id, latestMenu[0].id))
    
    return c.json({
      menu: latestMenu[0],
      items: menuItems
    })
  } catch (_error) {
    return c.json({ error: 'Failed to fetch menu' }, 500)
  }
})

export default brands