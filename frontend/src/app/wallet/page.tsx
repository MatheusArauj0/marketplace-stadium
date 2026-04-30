"use client";

import { useState, useEffect, useCallback } from "react";
import { useRouter } from "next/navigation";
import api from "@/lib/api";
import { useAuth } from "@/contexts/AuthContext";
import Button from "@/components/ui/Button";
import Input from "@/components/ui/Input";
import { formatCurrency, formatDate } from "@/lib/utils";
import { Wallet, ArrowUpCircle, ArrowDownCircle, Plus, Loader2 } from "lucide-react";
import type { Wallet as WalletType, Transaction, Page } from "@/types";
import toast from "react-hot-toast";

export default function WalletPage() {
  const router = useRouter();
  const { isAuthenticated } = useAuth();

  const [wallet, setWallet] = useState<WalletType | null>(null);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [loading, setLoading] = useState(true);
  const [showRecharge, setShowRecharge] = useState(false);
  const [amount, setAmount] = useState("");
  const [recharging, setRecharging] = useState(false);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchWallet = useCallback(async () => {
    try {
      const { data } = await api.get<WalletType>("/api/v1/wallet");
      setWallet(data);
    } catch {
      // Wallet may not exist yet
    }
  }, []);

  const fetchTransactions = useCallback(async () => {
    try {
      const { data } = await api.get<Page<Transaction>>("/api/v1/wallet/transactions", {
        params: { page, size: 15, sort: "createdAt,desc" },
      });
      setTransactions(data.content);
      setTotalPages(data.totalPages);
    } catch {
      /* ignore */
    }
  }, [page]);

  useEffect(() => {
    if (!isAuthenticated) {
      router.push("/login");
      return;
    }
    Promise.all([fetchWallet(), fetchTransactions()]).finally(() => setLoading(false));
  }, [isAuthenticated, router, fetchWallet, fetchTransactions]);

  const handleCreateWallet = async () => {
    try {
      const { data } = await api.post<WalletType>("/api/v1/wallet");
      setWallet(data);
      toast.success("Carteira criada!");
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Erro ao criar carteira");
    }
  };

  const handleRecharge = async () => {
    const value = parseFloat(amount.replace(",", "."));
    if (!value || value <= 0) {
      toast.error("Informe um valor válido");
      return;
    }
    setRecharging(true);
    try {
      const { data } = await api.post<WalletType>("/api/v1/wallet/credit", {
        amount: value,
      });
      setWallet(data);
      setShowRecharge(false);
      setAmount("");
      await fetchTransactions();
      toast.success(`${formatCurrency(value)} adicionados!`);
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Erro na recarga");
    } finally {
      setRecharging(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center py-20">
        <Loader2 className="h-8 w-8 animate-spin text-primary-600" />
      </div>
    );
  }

  if (!wallet) {
    return (
      <div className="flex flex-col items-center justify-center py-20 text-center">
        <Wallet className="mb-4 h-16 w-16 text-slate-300" />
        <h2 className="text-lg font-semibold text-slate-700">Crie sua carteira</h2>
        <p className="mt-1 text-sm text-slate-400">
          Sua carteira digital para compras no estádio
        </p>
        <Button onClick={handleCreateWallet} className="mt-4">
          <Plus className="h-4 w-4" /> Criar Carteira
        </Button>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-bold text-slate-900">Carteira</h1>

      {/* Balance card */}
      <div className="rounded-2xl bg-gradient-to-br from-primary-600 to-primary-800 p-5 text-white">
        <p className="text-sm font-medium text-primary-200">Saldo disponível</p>
        <p className="mt-1 text-3xl font-bold">{formatCurrency(wallet.balance)}</p>
        <Button
          onClick={() => setShowRecharge(!showRecharge)}
          variant="secondary"
          size="sm"
          className="mt-4"
        >
          <Plus className="h-4 w-4" /> Recarregar
        </Button>
      </div>

      {/* Recharge form */}
      {showRecharge && (
        <div className="rounded-2xl border border-slate-200 bg-white p-4 space-y-3">
          <h2 className="text-sm font-semibold text-slate-700">Recarregar Carteira</h2>
          <div className="flex gap-2">
            {[20, 50, 100, 200].map((v) => (
              <button
                key={v}
                onClick={() => setAmount(String(v))}
                className={`flex-1 rounded-xl border py-2 text-sm font-semibold transition-all ${
                  amount === String(v)
                    ? "border-primary-500 bg-primary-50 text-primary-700"
                    : "border-slate-200 text-slate-600 hover:border-slate-300"
                }`}
              >
                R${v}
              </button>
            ))}
          </div>
          <Input
            id="amount"
            label="Ou digite o valor"
            type="number"
            placeholder="0,00"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            min={1}
            step="0.01"
          />
          <Button onClick={handleRecharge} loading={recharging} className="w-full">
            Confirmar Recarga
          </Button>
        </div>
      )}

      {/* Transactions */}
      <div className="rounded-2xl border border-slate-200 bg-white p-4">
        <h2 className="mb-3 text-sm font-semibold text-slate-700">Extrato</h2>
        {transactions.length === 0 ? (
          <p className="text-center text-sm text-slate-400 py-6">Nenhuma transação</p>
        ) : (
          <div className="space-y-3">
            {transactions.map((tx) => (
              <div key={tx.id} className="flex items-center gap-3">
                <div
                  className={`flex h-9 w-9 shrink-0 items-center justify-center rounded-full ${
                    tx.type === "CREDIT"
                      ? "bg-green-100 text-green-600"
                      : "bg-red-100 text-red-600"
                  }`}
                >
                  {tx.type === "CREDIT" ? (
                    <ArrowUpCircle className="h-4 w-4" />
                  ) : (
                    <ArrowDownCircle className="h-4 w-4" />
                  )}
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium text-slate-900 truncate">
                    {tx.description}
                  </p>
                  <p className="text-xs text-slate-400">{formatDate(tx.createdAt)}</p>
                </div>
                <span
                  className={`text-sm font-bold ${
                    tx.type === "CREDIT" ? "text-green-600" : "text-red-600"
                  }`}
                >
                  {tx.type === "CREDIT" ? "+" : "-"}
                  {formatCurrency(tx.amount)}
                </span>
              </div>
            ))}
          </div>
        )}

        {totalPages > 1 && (
          <div className="flex items-center justify-center gap-2 pt-4 mt-3 border-t border-slate-100">
            <button
              onClick={() => setPage((p) => Math.max(0, p - 1))}
              disabled={page === 0}
              className="rounded-lg border border-slate-200 px-3 py-1 text-xs disabled:opacity-40"
            >
              Anterior
            </button>
            <span className="text-xs text-slate-400">
              {page + 1}/{totalPages}
            </span>
            <button
              onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
              disabled={page >= totalPages - 1}
              className="rounded-lg border border-slate-200 px-3 py-1 text-xs disabled:opacity-40"
            >
              Próximo
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
