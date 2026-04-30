"use client";

import Link from "next/link";
import { formatCurrency } from "@/lib/utils";
import { ShoppingCart, Package } from "lucide-react";
import type { Product } from "@/types";

interface ProductCardProps {
  product: Product;
  onAddToCart: (productId: string) => void;
  addingId: string | null;
}

export default function ProductCard({ product, onAddToCart, addingId }: ProductCardProps) {
  const outOfStock = product.quantidade <= 0;

  return (
    <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm transition-shadow hover:shadow-md">
      <Link href={`/catalog/${product.id}`}>
        <div className="relative aspect-[4/3] bg-slate-100">
          {product.imgUrl ? (
            <img
              src={product.imgUrl}
              alt={product.name}
              className="h-full w-full object-cover"
            />
          ) : (
            <div className="flex h-full items-center justify-center">
              <Package className="h-12 w-12 text-slate-300" />
            </div>
          )}
          {outOfStock && (
            <div className="absolute inset-0 flex items-center justify-center bg-black/50">
              <span className="rounded-full bg-red-500 px-3 py-1 text-xs font-bold text-white">
                Esgotado
              </span>
            </div>
          )}
        </div>
      </Link>

      <div className="p-3">
        <div className="flex flex-wrap gap-1 mb-1.5">
          {product.categories?.map((cat) => (
            <span
              key={cat.id}
              className="rounded-full bg-primary-50 px-2 py-0.5 text-[10px] font-medium text-primary-700"
            >
              {cat.name}
            </span>
          ))}
        </div>

        <Link href={`/catalog/${product.id}`}>
          <h3 className="text-sm font-semibold text-slate-900 line-clamp-1">{product.name}</h3>
        </Link>

        {product.description && (
          <p className="mt-0.5 text-xs text-slate-500 line-clamp-2">{product.description}</p>
        )}

        <div className="mt-2 flex items-center justify-between">
          <span className="text-lg font-bold text-primary-600">
            {formatCurrency(product.price)}
          </span>
          <button
            onClick={() => onAddToCart(product.id)}
            disabled={outOfStock || addingId === product.id}
            className="flex h-9 w-9 items-center justify-center rounded-xl bg-primary-600 text-white transition-all hover:bg-primary-700 active:scale-95 disabled:opacity-40"
          >
            <ShoppingCart className="h-4 w-4" />
          </button>
        </div>
      </div>
    </div>
  );
}
