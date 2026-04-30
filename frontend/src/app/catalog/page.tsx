"use client";

import { useState, useEffect, useCallback } from "react";
import api from "@/lib/api";
import type { Product, Category, Page } from "@/types";
import ProductCard from "@/components/catalog/ProductCard";
import { useAuth } from "@/contexts/AuthContext";
import { useCart } from "@/contexts/CartContext";
import { Search, Loader2 } from "lucide-react";
import toast from "react-hot-toast";

export default function CatalogPage() {
  const { isAuthenticated } = useAuth();
  const { addItem, fetchCart } = useCart();

  const [products, setProducts] = useState<Product[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);
  const [search, setSearch] = useState("");
  const [loading, setLoading] = useState(true);
  const [addingId, setAddingId] = useState<string | null>(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchProducts = useCallback(async () => {
    setLoading(true);
    try {
      const { data } = await api.get<Page<Product>>("/api/v1/products", {
        params: { page, size: 20, sort: "name,asc" },
      });
      setProducts(data.content);
      setTotalPages(data.totalPages);
    } catch {
      toast.error("Erro ao carregar produtos");
    } finally {
      setLoading(false);
    }
  }, [page]);

  const fetchCategories = useCallback(async () => {
    try {
      const { data } = await api.get<Page<Category>>("/api/v1/categories", {
        params: { size: 50, sort: "name,asc" },
      });
      setCategories(data.content);
    } catch {
      /* ignore */
    }
  }, []);

  useEffect(() => {
    fetchProducts();
    fetchCategories();
  }, [fetchProducts, fetchCategories]);

  useEffect(() => {
    if (isAuthenticated) fetchCart();
  }, [isAuthenticated, fetchCart]);

  const handleAddToCart = async (productId: string) => {
    if (!isAuthenticated) {
      toast.error("Faça login para adicionar ao carrinho");
      return;
    }
    setAddingId(productId);
    try {
      await addItem(productId, 1);
      toast.success("Adicionado ao carrinho!");
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Erro ao adicionar");
    } finally {
      setAddingId(null);
    }
  };

  const filtered = products.filter((p) => {
    const matchSearch =
      !search || p.name.toLowerCase().includes(search.toLowerCase());
    const matchCategory =
      !selectedCategory ||
      p.categories?.some((c) => c.id === selectedCategory);
    return matchSearch && matchCategory;
  });

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-bold text-slate-900">Cardápio</h1>

      {/* Search */}
      <div className="relative">
        <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-400" />
        <input
          type="text"
          placeholder="Buscar produto..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full rounded-xl border border-slate-200 bg-white py-2.5 pl-10 pr-4 text-sm placeholder:text-slate-400 focus:border-primary-500 focus:outline-none focus:ring-2 focus:ring-primary-500/20"
        />
      </div>

      {/* Category chips */}
      <div className="flex gap-2 overflow-x-auto scrollbar-hide pb-1">
        <button
          onClick={() => setSelectedCategory(null)}
          className={`shrink-0 rounded-full px-3 py-1.5 text-xs font-semibold transition-all ${
            !selectedCategory
              ? "bg-primary-600 text-white"
              : "bg-slate-100 text-slate-600 hover:bg-slate-200"
          }`}
        >
          Todos
        </button>
        {categories.map((cat) => (
          <button
            key={cat.id}
            onClick={() =>
              setSelectedCategory(selectedCategory === cat.id ? null : cat.id)
            }
            className={`shrink-0 rounded-full px-3 py-1.5 text-xs font-semibold transition-all ${
              selectedCategory === cat.id
                ? "bg-primary-600 text-white"
                : "bg-slate-100 text-slate-600 hover:bg-slate-200"
            }`}
          >
            {cat.name}
          </button>
        ))}
      </div>

      {/* Product grid */}
      {loading ? (
        <div className="flex items-center justify-center py-20">
          <Loader2 className="h-8 w-8 animate-spin text-primary-600" />
        </div>
      ) : filtered.length === 0 ? (
        <div className="py-20 text-center text-sm text-slate-400">
          Nenhum produto encontrado
        </div>
      ) : (
        <>
          <div className="grid grid-cols-2 gap-3">
            {filtered.map((product) => (
              <ProductCard
                key={product.id}
                product={product}
                onAddToCart={handleAddToCart}
                addingId={addingId}
              />
            ))}
          </div>

          {/* Pagination */}
          {totalPages > 1 && (
            <div className="flex items-center justify-center gap-2 pt-4">
              <button
                onClick={() => setPage((p) => Math.max(0, p - 1))}
                disabled={page === 0}
                className="rounded-lg border border-slate-200 px-3 py-1.5 text-sm disabled:opacity-40"
              >
                Anterior
              </button>
              <span className="text-sm text-slate-500">
                {page + 1} / {totalPages}
              </span>
              <button
                onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
                disabled={page >= totalPages - 1}
                className="rounded-lg border border-slate-200 px-3 py-1.5 text-sm disabled:opacity-40"
              >
                Próximo
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
}
