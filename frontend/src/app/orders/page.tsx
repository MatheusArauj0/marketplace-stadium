"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import api from "@/lib/api";
import { useAuth } from "@/contexts/AuthContext";
import { formatCurrency, formatDate, statusLabel, statusColor } from "@/lib/utils";
import Badge from "@/components/ui/Badge";
import { ClipboardList, ChevronRight, Loader2 } from "lucide-react";
import type { OrderSummary } from "@/types";

export default function OrdersPage() {
  const router = useRouter();
  const { isAuthenticated } = useAuth();
  const [orders, setOrders] = useState<OrderSummary[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!isAuthenticated) {
      router.push("/login");
      return;
    }
    api
      .get<OrderSummary[]>("/api/v1/order/my-orders")
      .then(({ data }) => setOrders(data))
      .catch(() => {})
      .finally(() => setLoading(false));
  }, [isAuthenticated, router]);

  if (loading) {
    return (
      <div className="flex items-center justify-center py-20">
        <Loader2 className="h-8 w-8 animate-spin text-primary-600" />
      </div>
    );
  }

  if (orders.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center py-20 text-center">
        <ClipboardList className="mb-4 h-16 w-16 text-slate-300" />
        <h2 className="text-lg font-semibold text-slate-700">Nenhum pedido</h2>
        <p className="mt-1 text-sm text-slate-400">Seus pedidos aparecerão aqui</p>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-bold text-slate-900">Meus Pedidos</h1>

      <div className="space-y-3">
        {orders.map((order) => (
          <Link
            key={order.orderId}
            href={`/orders/${order.orderId}`}
            className="flex items-center gap-3 rounded-2xl border border-slate-200 bg-white p-4 transition-shadow hover:shadow-md"
          >
            <div className="flex-1 min-w-0 space-y-1.5">
              <div className="flex items-center gap-2">
                <Badge className={statusColor(order.status)}>
                  {statusLabel(order.status)}
                </Badge>
                <span className="text-xs text-slate-400">
                  {formatDate(order.createdAt)}
                </span>
              </div>
              <p className="text-sm text-slate-600 truncate">{order.itemsSummary}</p>
              <div className="flex items-center gap-2">
                <span className="text-sm font-bold text-slate-900">
                  {formatCurrency(order.total)}
                </span>
                <span className="text-xs text-slate-400">
                  · {order.itemCount} {order.itemCount === 1 ? "item" : "itens"}
                </span>
              </div>
              {order.pickupCode && (
                <div className="inline-block rounded-lg bg-primary-50 px-2 py-0.5">
                  <span className="text-xs font-mono font-bold text-primary-700">
                    #{order.pickupCode}
                  </span>
                </div>
              )}
            </div>
            <ChevronRight className="h-5 w-5 shrink-0 text-slate-400" />
          </Link>
        ))}
      </div>
    </div>
  );
}
