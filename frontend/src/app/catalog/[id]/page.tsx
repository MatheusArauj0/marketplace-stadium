"use client";

import { useState, useEffect } from "react";
import { useParams, useRouter } from "next/navigation";
import api from "@/lib/api";
import { useAuth } from "@/contexts/AuthContext";
import { useCart } from "@/contexts/CartContext";
import Button from "@/components/ui/Button";
import { formatCurrency } from "@/lib/utils";
import { ArrowLeft, Package, Minus, Plus, ShoppingCart } from "lucide-react";
import type { Product } from "@/types";
import toast from "react-hot-toast";

export default function ProductDetailPage() {
  const { id } = useParams<{ id: string }>();
  const router = useRouter();
  const { isAuthenticated } = useAuth();
  const { addItem } = useCart();

  const [product, setProduct] = useState<Product | null>(null);
  const [quantity, setQuantity] = useState(1);
  const [loading, setLoading] = useState(true);
  const [adding, setAdding] = useState(false);

  useEffect(() => {
    api
      .get<Product>(`/api/v1/products/${id}`)
      .then(({ data }) => setProduct(data))
      .catch(() => toast.error("Produto não encontrado"))
      .finally(() => setLoading(false));
  }, [id]);

  const handleAdd = async () => {
    if (!isAuthenticated) {
      toast.error("Faça login para adicionar ao carrinho");
      return;
    }
    setAdding(true);
    try {
      await addItem(id, quantity);
      toast.success(`${quantity}x ${product?.name} adicionado!`);
      router.push("/cart");
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Erro ao adicionar");
    } finally {
      setAdding(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center py-20">
        <div className="h-8 w-8 animate-spin rounded-full border-4 border-primary-200 border-t-primary-600" />
      </div>
    );
  }

  if (!product) {
    return (
      <div className="py-20 text-center text-slate-500">Produto não encontrado</div>
    );
  }

  const outOfStock = product.quantidade <= 0;

  return (
    <div className="space-y-4">
      <button
        onClick={() => router.back()}
        className="flex items-center gap-1 text-sm text-slate-500 hover:text-slate-700"
      >
        <ArrowLeft className="h-4 w-4" /> Voltar
      </button>

      <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white">
        <div className="relative aspect-video bg-slate-100">
          {product.imgUrl ? (
            <img src={product.imgUrl} alt={product.name} className="h-full w-full object-cover" />
          ) : (
            <div className="flex h-full items-center justify-center">
              <Package className="h-16 w-16 text-slate-300" />
            </div>
          )}
        </div>

        <div className="p-4 space-y-3">
          <div className="flex flex-wrap gap-1">
            {product.categories?.map((cat) => (
              <span
                key={cat.id}
                className="rounded-full bg-primary-50 px-2.5 py-0.5 text-xs font-medium text-primary-700"
              >
                {cat.name}
              </span>
            ))}
          </div>

          <h1 className="text-xl font-bold text-slate-900">{product.name}</h1>

          {product.description && (
            <p className="text-sm text-slate-600">{product.description}</p>
          )}

          <div className="flex items-center justify-between">
            <span className="text-2xl font-bold text-primary-600">
              {formatCurrency(product.price)}
            </span>
            <span className="text-xs text-slate-400">
              {outOfStock
                ? "Esgotado"
                : `${product.quantidade} disponíveis`}
            </span>
          </div>

          {!outOfStock && (
            <>
              <div className="flex items-center justify-center gap-4 rounded-xl bg-slate-50 py-3">
                <button
                  onClick={() => setQuantity((q) => Math.max(1, q - 1))}
                  className="flex h-8 w-8 items-center justify-center rounded-lg bg-white border border-slate-200 text-slate-600"
                >
                  <Minus className="h-4 w-4" />
                </button>
                <span className="w-8 text-center text-lg font-bold">{quantity}</span>
                <button
                  onClick={() =>
                    setQuantity((q) => Math.min(product.quantidade, q + 1))
                  }
                  className="flex h-8 w-8 items-center justify-center rounded-lg bg-white border border-slate-200 text-slate-600"
                >
                  <Plus className="h-4 w-4" />
                </button>
              </div>

              <Button
                onClick={handleAdd}
                loading={adding}
                size="lg"
                className="w-full"
              >
                <ShoppingCart className="h-4 w-4" />
                Adicionar {formatCurrency(product.price * quantity)}
              </Button>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
