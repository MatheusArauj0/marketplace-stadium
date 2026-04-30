"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import api from "@/lib/api";
import { useAuth } from "@/contexts/AuthContext";
import { useCart } from "@/contexts/CartContext";
import Button from "@/components/ui/Button";
import { formatCurrency } from "@/lib/utils";
import { Wallet, CreditCard, CheckCircle2 } from "lucide-react";
import type { CheckoutResponse, Wallet as WalletType } from "@/types";
import toast from "react-hot-toast";

export default function CheckoutPage() {
  const router = useRouter();
  const { isAuthenticated } = useAuth();
  const { items, total, fetchCart } = useCart();

  const [paymentMethod, setPaymentMethod] = useState("WALLET");
  const [wallet, setWallet] = useState<WalletType | null>(null);
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState<CheckoutResponse | null>(null);

  useEffect(() => {
    if (!isAuthenticated) {
      router.push("/login");
      return;
    }
    api
      .get<WalletType>("/api/v1/wallet")
      .then(({ data }) => setWallet(data))
      .catch(() => {});
    fetchCart();
  }, [isAuthenticated, router, fetchCart]);

  const handleCheckout = async () => {
    setLoading(true);
    try {
      const { data } = await api.post<CheckoutResponse>("/api/v1/order/checkout", {
        paymentMethod,
      });
      setResult(data);
      await fetchCart();
      toast.success("Pedido realizado com sucesso!");
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Erro ao finalizar pedido");
    } finally {
      setLoading(false);
    }
  };

  // Success screen
  if (result) {
    return (
      <div className="flex min-h-[60dvh] flex-col items-center justify-center text-center">
        <div className="mb-4 flex h-20 w-20 items-center justify-center rounded-full bg-green-100">
          <CheckCircle2 className="h-10 w-10 text-green-600" />
        </div>
        <h1 className="text-xl font-bold text-slate-900">Pedido Confirmado!</h1>
        <p className="mt-2 text-sm text-slate-500">
          Seu código de retirada é:
        </p>
        <div className="mt-3 rounded-2xl bg-primary-50 border-2 border-primary-200 px-6 py-3">
          <span className="text-2xl font-mono font-bold text-primary-700 tracking-widest">
            {result.pickupCode}
          </span>
        </div>
        <p className="mt-3 text-sm text-slate-500">
          Total: <strong>{formatCurrency(result.total)}</strong>
        </p>
        <div className="mt-6 flex gap-3">
          <Button onClick={() => router.push(`/orders/${result.orderId}`)} variant="primary">
            Ver Pedido
          </Button>
          <Button onClick={() => router.push("/catalog")} variant="outline">
            Continuar Comprando
          </Button>
        </div>
      </div>
    );
  }

  if (items.length === 0) {
    router.push("/cart");
    return null;
  }

  const insufficientBalance = wallet && wallet.balance < total;

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-bold text-slate-900">Finalizar Pedido</h1>

      {/* Order summary */}
      <div className="rounded-2xl border border-slate-200 bg-white p-4">
        <h2 className="mb-3 text-sm font-semibold text-slate-700">Resumo do Pedido</h2>
        <div className="space-y-2">
          {items.map((item) => (
            <div key={item.id} className="flex items-center justify-between text-sm">
              <span className="text-slate-600">
                {item.quantity}x {item.productName}
              </span>
              <span className="font-medium text-slate-900">
                {formatCurrency(item.subtotal)}
              </span>
            </div>
          ))}
          <div className="border-t border-slate-100 pt-2 mt-2 flex items-center justify-between">
            <span className="font-semibold text-slate-900">Total</span>
            <span className="text-lg font-bold text-primary-600">{formatCurrency(total)}</span>
          </div>
        </div>
      </div>

      {/* Payment method */}
      <div className="rounded-2xl border border-slate-200 bg-white p-4">
        <h2 className="mb-3 text-sm font-semibold text-slate-700">Forma de Pagamento</h2>
        <div className="space-y-2">
          <label
            className={`flex cursor-pointer items-center gap-3 rounded-xl border-2 p-3 transition-all ${
              paymentMethod === "WALLET"
                ? "border-primary-500 bg-primary-50"
                : "border-slate-200 hover:border-slate-300"
            }`}
          >
            <input
              type="radio"
              name="payment"
              value="WALLET"
              checked={paymentMethod === "WALLET"}
              onChange={(e) => setPaymentMethod(e.target.value)}
              className="sr-only"
            />
            <Wallet className="h-5 w-5 text-primary-600" />
            <div className="flex-1">
              <span className="text-sm font-semibold text-slate-900">Carteira Digital</span>
              {wallet && (
                <p className="text-xs text-slate-500">
                  Saldo: {formatCurrency(wallet.balance)}
                </p>
              )}
            </div>
            <div
              className={`h-4 w-4 rounded-full border-2 ${
                paymentMethod === "WALLET"
                  ? "border-primary-600 bg-primary-600"
                  : "border-slate-300"
              }`}
            />
          </label>

          <label
            className={`flex cursor-pointer items-center gap-3 rounded-xl border-2 p-3 transition-all ${
              paymentMethod === "MERCADO_PAGO"
                ? "border-primary-500 bg-primary-50"
                : "border-slate-200 hover:border-slate-300"
            }`}
          >
            <input
              type="radio"
              name="payment"
              value="MERCADO_PAGO"
              checked={paymentMethod === "MERCADO_PAGO"}
              onChange={(e) => setPaymentMethod(e.target.value)}
              className="sr-only"
            />
            <CreditCard className="h-5 w-5 text-blue-600" />
            <div className="flex-1">
              <span className="text-sm font-semibold text-slate-900">Mercado Pago</span>
              <p className="text-xs text-slate-500">Cartão ou Pix</p>
            </div>
            <div
              className={`h-4 w-4 rounded-full border-2 ${
                paymentMethod === "MERCADO_PAGO"
                  ? "border-primary-600 bg-primary-600"
                  : "border-slate-300"
              }`}
            />
          </label>
        </div>
      </div>

      {insufficientBalance && paymentMethod === "WALLET" && (
        <div className="rounded-xl bg-red-50 border border-red-200 p-3 text-sm text-red-700">
          Saldo insuficiente. Recarregue sua carteira ou escolha outro método.
        </div>
      )}

      <Button
        onClick={handleCheckout}
        loading={loading}
        disabled={insufficientBalance && paymentMethod === "WALLET"}
        size="lg"
        className="w-full"
      >
        Confirmar Pedido — {formatCurrency(total)}
      </Button>
    </div>
  );
}
