import {MenuList} from '@/components/MenuList';
import {BrandList} from '@/components/BrandList';
import tryCatch from "@/utils/tryCatch";
import {mainApiFetch} from "@/utils/fetch";

export default async function Home() {
    const { data: brandsData, error: brandsError } = await tryCatch(
        mainApiFetch(`/api/brands`)
    );

    const { data: menusData, error: menusError } = await tryCatch(
        mainApiFetch(`/api/menus`)
    );

    if(brandsError || menusError) {
        return (
            <div className="min-h-screen bg-gray-50 p-4 flex items-center justify-center">
                <div className="text-center">
                    <h1 className="text-2xl font-bold text-gray-900 mb-4">🍗 Chicken Menu Hub</h1>
                    <p className="text-red-600">Failed to load data. Please try again later.</p>
                </div>
            </div>
        );
    }


  return (
    <div className="min-h-screen bg-gray-50 p-4">
      <div className="max-w-6xl mx-auto">
        <header className="mb-8 text-center">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">🍗 Chicken Menu Hub</h1>
          <p className="text-gray-600">Latest menus from all your favorite chicken brands</p>
        </header>
        
        <div className="grid grid-cols-1 lg:grid-cols-4 gap-6">
          <aside className="lg:col-span-1">
            <BrandList brands={brandsData || []} />
          </aside>
          
          <main className="lg:col-span-3">
            <MenuList menus={menusData || []} />
          </main>
        </div>
      </div>
    </div>
  );
}
