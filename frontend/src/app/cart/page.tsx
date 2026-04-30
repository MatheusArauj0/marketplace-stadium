"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useCart } from "@/contexts/CartContext";
import { useAuth } from "@/contexts/AuthContext";
import Button from "@/components/ui/Button";
import { formatCurrency } from "@/lib/utils";
import { Minus, Plus, Trash2, ShoppingBag, ArrowRight } from "lucide-react";
import toast from "react-hot-toast";

export default function CartPage() {
  const router = useRouter();
  const { isAuthenticated } = useAuth();
  const { items, total, loading, fetchCart, updateItem, removeItem } = useCart();
  const [updatingId, setUpdatingId] = useState<string | null>(null);

  useEffect(() => {
    if (isAuthenticated) fetchCart();
  }, [isAuthenticated, fetchCart]);

  useEffect(() => {
    if (!isAuthenticated) router.push("/login");
  }, [isAuthenticated, router]);

  const handleUpdate = async (itemId: string, newQty: number) => {
    if (newQty < 1) return;
    setUpdatingId(itemId);
    try {
      await updateItem(itemId, newQty);
    } catch {
      toast.error("Erro ao atualizar quantidade");
    } finally {
      setUpdatingId(null);
    }
  };

  const handleRemove = async (itemId: string) => {
    try {
      await removeItem(itemId);
      toast.success("Item removido");
    } catch {
      toast.error("Erro ao remover item");
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center py-20">
        <div className="h-8 w-8 animate-spin rounded-full border-4 border-primary-200 border-t-primary-600" />
      </div>
    );
  }

  if (items.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center py-20 text-center">
        <ShoppingBag className="mb-4 h-16 w-16 text-slate-300" />
        <h2 className="text-lg font-semibold text-slate-700">Carrinho vazio</h2>
        <p className="mt-1 text-sm text-slate-400">Adicione produtos do cardápio</p>
        <Button onClick={() => router.push("/catalog")} className="mt-4" variant="outline">
          Ver Cardápio
        </Button>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-bold text-slate-900">Carrinho</h1>

      <div className="space-y-3">
        {items.map((item) => (
          <div
            key={item.id}
            className="flex items-center gap-3 rounded-2xl border border-slate-200 bg-white p-3"
          >
            <div className="h-16 w-16 shrink-0 overflow-hidden rounded-xl bg-slate-100">
              {item.productImgUrl ? (
                <img
                  src={item.productImgUrl}
                  alt={item.productName}
                  className="h-full w-full object-cover"
                />
              ) : (
                <div className="flex h-full items-center justify-center text-slate-300">
                  <ShoppingBag className="h-6 w-6" />
                </div>
              )}
            </div>

            <div className="flex-1 min-w-0">
              <h3 className="text-sm font-semibold text-slate-900 truncate">
                {item.productName}
              </h3>
              <p className="text-sm font-bold text-primary-600">
                {formatCurrency(item.unitPrice)}
              </p>
            </div>

            <div className="flex items-center gap-1.5">
              <button
                onClick={() => handleUpdate(item.id, item.quantity - 1)}
                disabled={item.quantity <= 1 || updatingId === item.id}
                className="flex h-7 w-7 items-center justify-center rounded-lg border border-slate-200 text-slate-500 disabled:opacity-40"
              >
                <Minus className="h-3 w-3" />
              </button>
              <span className="w-6 text-center text-sm font-bold">{item.quantity}</span>
              <button
                onClick={() => handleUpdate(item.id, item.quantity + 1)}
                disabled={updatingId === item.id}
                className="flex h-7 w-7 items-center justify-center rounded-lg border border-slate-200 text-slate-500"
              >
                <Plus className="h-3 w-3" />
              </button>
            </div>

            <button
              onClick={() => handleRemove(item.id)}
              className="p-1.5 text-slate-400 hover:text-red-500"
            >
              <Trash2 className="h-4 w-4" />
            </button>
          </div>
        ))}
      </div>

      {/* Total + Checkout */}
      <div className="rounded-2xl border border-slate-200 bg-white p-4 space-y-3">
        <div className="flex items-center justify-between">
          <span className="text-sm text-slate-500">Total</span>
          <span className="text-xl font-bold text-slate-900">{formatCurrency(total)}</span>
        </div>
        <Button
          onClick={() => router.push("/checkout")}
          size="lg"
          className="w-full"
        >
          Finalizar Pedido <ArrowRight className="h-4 w-4" />
        </Button>
      </div>
    </div>
  );
}
