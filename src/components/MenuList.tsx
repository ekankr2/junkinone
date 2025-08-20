interface MenuData {
  menuId: number;
  brandId: number;
  brandName: string;
  brandWebsite: string;
  menuDate: Date;
  itemName: string | null;
  price: string | null;
  category: string | null;
  description: string | null;
  available: number | null;
}

interface GroupedMenu {
  brandId: number;
  brandName: string;
  menuDate: string;
  items: {
    name: string;
    price: string;
    category: string;
    description: string;
    available: boolean;
  }[];
}

interface MenuListProps {
  menus: MenuData[];
}

export function MenuList({ menus: menuData }: MenuListProps) {
  const groupMenusByBrand = (data: MenuData[]): GroupedMenu[] => {
    const grouped = data.reduce((acc, item) => {
      if (!acc[item.brandId]) {
        acc[item.brandId] = {
          brandId: item.brandId,
          brandName: item.brandName,
          menuDate: item.menuDate.toISOString(),
          items: []
        };
      }
      
      if (item.itemName) {
        acc[item.brandId].items.push({
          name: item.itemName,
          price: item.price || '',
          category: item.category || '',
          description: item.description || '',
          available: item.available === 1
        });
      }
      
      return acc;
    }, {} as Record<number, GroupedMenu>);

    return Object.values(grouped);
  };

  const menus = groupMenusByBrand(menuData);

  return (
    <div className="space-y-6">
      {menus.map((menu) => (
        <div key={menu.brandId} className="bg-white rounded-lg shadow p-6">
          <div className="flex justify-between items-center mb-4">
            <h2 className="text-2xl font-bold text-gray-900">{menu.brandName}</h2>
            <span className="text-sm text-gray-500">
              Updated: {new Date(menu.menuDate).toLocaleDateString()}
            </span>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {menu.items.map((item, index) => (
              <div 
                key={index} 
                className={`border rounded-lg p-4 ${item.available ? 'bg-white' : 'bg-gray-100 opacity-75'}`}
              >
                <div className="flex justify-between items-start mb-2">
                  <h3 className="font-medium text-gray-900">{item.name}</h3>
                  {item.price && (
                    <span className="text-lg font-semibold text-orange-600">
                      ₩{parseFloat(item.price).toLocaleString()}
                    </span>
                  )}
                </div>
                
                {item.category && (
                  <span className="inline-block bg-gray-200 text-gray-700 text-xs px-2 py-1 rounded mb-2">
                    {item.category}
                  </span>
                )}
                
                {item.description && (
                  <p className="text-sm text-gray-600 mb-2">{item.description}</p>
                )}
                
                {!item.available && (
                  <span className="text-red-500 text-xs font-medium">Currently Unavailable</span>
                )}
              </div>
            ))}
          </div>
          
          {menu.items.length === 0 && (
            <p className="text-gray-500 text-center py-8">No menu items found for this brand</p>
          )}
        </div>
      ))}
      
      {menus.length === 0 && (
        <div className="bg-white rounded-lg shadow p-6 text-center">
          <h2 className="text-xl font-semibold mb-4">No Menus Available</h2>
          <p className="text-gray-600">Menu data will be updated automatically via cron jobs.</p>
        </div>
      )}
    </div>
  );
}