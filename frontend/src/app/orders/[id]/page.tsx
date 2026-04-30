"use client";

import { useState, useEffect } from "react";
import { useParams, useRouter } from "next/navigation";
import api from "@/lib/api";
import { useAuth } from "@/contexts/AuthContext";
import { formatCurrency, formatDate, statusLabel, statusColor } from "@/lib/utils";
import Badge from "@/components/ui/Badge";
import Button from "@/components/ui/Button";
import { ArrowLeft, Package, Loader2 } from "lucide-react";
import type { OrderDetail } from "@/types";

export default function OrderDetailPage() {
  const { id } = useParams<{ id: string }>();
  const router = useRouter();
  const { isAuthenticated } = useAuth();
  const [order, setOrder] = useState<OrderDetail | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!isAuthenticated) {
      router.push("/login");
      return;
    }
    api
      .get<OrderDetail>(`/api/v1/order/${id}`)
      .then(({ data }) => setOrder(data))
      .catch(() => {})
      .finally(() => setLoading(false));
  }, [id, isAuthenticated, router]);

  if (loading) {
    return (
      <div className="flex items-center justify-center py-20">
        <Loader2 className="h-8 w-8 animate-spin text-primary-600" />
      </div>
    );
  }

  if (!order) {
    return <div className="py-20 text-center text-slate-500">Pedido não encontrado</div>;
  }

  return (
    <div className="space-y-4">
      <button
        onClick={() => router.back()}
        className="flex items-center gap-1 text-sm text-slate-500 hover:text-slate-700"
      >
        <ArrowLeft className="h-4 w-4" /> Voltar
      </button>

      {/* QR Code for pickup */}
      {order.pickupCode && (
        <div className="flex flex-col items-center rounded-2xl border border-slate-200 bg-white p-6">
          <p className="mb-3 text-sm font-medium text-slate-500">
            Apresente este código na retirada
          </p>
          {/* QR Code rendered as a styled code block - qrcode.react will be used when npm install runs */}
          <div className="rounded-2xl bg-white border-2 border-slate-200 p-4 shadow-inner">
            <div className="flex items-center justify-center" style={{ width: 200, height: 200 }}>
              {/* Fallback: large pickup code display */}
              <div className="text-center">
                <img
                  src={`https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${encodeURIComponent(order.pickupCode)}`}
                  alt="QR Code"
                  width={200}
                  height={200}
                  className="rounded-lg"
                />
              </div>
            </div>
          </div>
          <div className="mt-3 rounded-xl bg-primary-50 border border-primary-200 px-4 py-2">
            <span className="text-xl font-mono font-bold text-primary-700 tracking-[0.2em]">
              {order.pickupCode}
            </span>
          </div>
        </div>
      )}

      {/* Status & Info */}
      <div className="rounded-2xl border border-slate-200 bg-white p-4 space-y-3">
        <div className="flex items-center justify-between">
          <Badge className={statusColor(order.status)}>{statusLabel(order.status)}</Badge>
          <span className="text-xs text-slate-400">{formatDate(order.createdAt)}</span>
        </div>
        <div className="grid grid-cols-2 gap-3 text-sm">
          <div>
            <span className="text-slate-500">Pagamento</span>
            <p className="font-medium text-slate-900">
              {order.paymentMethod === "WALLET" ? "Carteira" : "Mercado Pago"}
            </p>
          </div>
          <div>
            <span className="text-slate-500">Status Pgto</span>
            <p className="font-medium text-slate-900">{order.paymentStatus}</p>
          </div>
        </div>
      </div>

      {/* Items */}
      <div className="rounded-2xl border border-slate-200 bg-white p-4">
        <h2 className="mb-3 text-sm font-semibold text-slate-700">Itens do Pedido</h2>
        <div className="space-y-3">
          {order.items.map((item, i) => (
            <div key={i} className="flex items-center gap-3">
              <div className="h-12 w-12 shrink-0 overflow-hidden rounded-xl bg-slate-100">
                {item.productImgUrl ? (
                  <img
                    src={item.productImgUrl}
                    alt={item.productName}
                    className="h-full w-full object-cover"
                  />
                ) : (
                  <div className="flex h-full items-center justify-center">
                    <Package className="h-5 w-5 text-slate-300" />
                  </div>
                )}
              </div>
              <div className="flex-1 min-w-0">
                <p className="text-sm font-medium text-slate-900 truncate">{item.productName}</p>
                <p className="text-xs text-slate-500">
                  {item.quantity}x {formatCurrency(item.unitPrice)}
                </p>
              </div>
              <span className="text-sm font-bold text-slate-900">
                {formatCurrency(item.subtotal)}
              </span>
            </div>
          ))}
        </div>

        <div className="mt-3 border-t border-slate-100 pt-3 flex items-center justify-between">
          <span className="font-semibold text-slate-900">Total</span>
          <span className="text-lg font-bold text-primary-600">{formatCurrency(order.total)}</span>
        </div>
      </div>

      <Button onClick={() => router.push("/catalog")} variant="outline" className="w-full">
        Fazer Novo Pedido
      </Button>
    </div>
  );
}
