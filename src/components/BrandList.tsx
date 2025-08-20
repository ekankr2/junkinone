interface Brand {
  id: number;
  name: string;
  website: string;
  scraping_config?: string | null;
  created_at?: Date | null;
}

interface BrandListProps {
  brands: Brand[];
}

export function BrandList({ brands }: BrandListProps) {
  return (
    <div className="bg-white rounded-lg shadow p-6">
      <div className="mb-4">
        <h2 className="text-xl font-semibold">Brands</h2>
        <p className="text-sm text-gray-600">Updated automatically via cron jobs</p>
      </div>
      
      <div className="space-y-3">
        {brands.map((brand) => (
          <div key={brand.id} className="border rounded-lg p-3">
            <h3 className="font-medium">{brand.name}</h3>
            <p className="text-sm text-gray-600 truncate">{brand.website}</p>
            <div className="mt-2">
              <span className="inline-flex items-center px-2 py-1 rounded-full text-xs bg-green-100 text-green-800">
                <div className="w-1.5 h-1.5 bg-green-400 rounded-full mr-1.5"></div>
                Active
              </span>
            </div>
          </div>
        ))}
        
        {brands.length === 0 && (
          <p className="text-gray-500 text-sm">No brands found</p>
        )}
      </div>
    </div>
  );
}