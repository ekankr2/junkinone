import { MenuList } from '@/components/MenuList';
import { BrandList } from '@/components/BrandList';
import { db } from '@/lib/database';
import { Brands, Menus, MenuItems } from '@/lib/models';
import { eq, sql } from 'drizzle-orm';

async function getBrands() {
  try {
    return await db.select().from(Brands);
  } catch (error) {
    console.warn('Database not available during build:', error);
    return [];
  }
}

async function getMenus() {
  try {
    return await db
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
      );
  } catch (error) {
    console.warn('Database not available during build:', error);
    return [];
  }
}

export default async function Home() {
  const [brands, menus] = await Promise.all([
    getBrands(),
    getMenus()
  ]);

  return (
    <div className="min-h-screen bg-gray-50 p-4">
      <div className="max-w-6xl mx-auto">
        <header className="mb-8 text-center">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">🍗 Chicken Menu Hub</h1>
          <p className="text-gray-600">Latest menus from all your favorite chicken brands</p>
        </header>
        
        <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
          <aside className="lg:col-span-1">
            <BrandList brands={brands} />
          </aside>
          
          <main className="lg:col-span-3">
            <MenuList menus={menus} />
          </main>
        </div>
      </div>
    </div>
  );
}
