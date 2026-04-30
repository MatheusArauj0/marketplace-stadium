"use client";

import { useRouter } from "next/navigation";
import { useAuth } from "@/contexts/AuthContext";
import Button from "@/components/ui/Button";
import { User, Mail, FileText, Shield, LogOut } from "lucide-react";
import { useEffect } from "react";

export default function ProfilePage() {
  const router = useRouter();
  const { user, logout, isAuthenticated, isSeller, isAdmin } = useAuth();

  useEffect(() => {
    if (!isAuthenticated) router.push("/login");
  }, [isAuthenticated, router]);

  if (!user) return null;

  const handleLogout = () => {
    logout();
    router.push("/login");
  };

  const formatDoc = (doc: string) => {
    if (doc?.length === 11) {
      return `${doc.slice(0, 3)}.${doc.slice(3, 6)}.${doc.slice(6, 9)}-${doc.slice(9)}`;
    }
    if (doc?.length === 14) {
      return `${doc.slice(0, 2)}.${doc.slice(2, 5)}.${doc.slice(5, 8)}/${doc.slice(8, 12)}-${doc.slice(12)}`;
    }
    return doc;
  };

  const roleLabel = () => {
    if (isAdmin) return "Administrador";
    if (isSeller) return "Lojista";
    return "Torcedor";
  };

  const roleColor = () => {
    if (isAdmin) return "bg-purple-100 text-purple-700";
    if (isSeller) return "bg-amber-100 text-amber-700";
    return "bg-primary-100 text-primary-700";
  };

  return (
    <div className="space-y-4">
      <h1 className="text-xl font-bold text-slate-900">Meu Perfil</h1>

      {/* Profile card */}
      <div className="rounded-2xl border border-slate-200 bg-white p-5">
        <div className="flex items-center gap-4 mb-4">
          <div className="flex h-14 w-14 items-center justify-center rounded-full bg-primary-100 text-primary-700">
            <User className="h-7 w-7" />
          </div>
          <div>
            <h2 className="text-lg font-bold text-slate-900">{user.name}</h2>
            <span className={`inline-flex items-center gap-1 rounded-full px-2 py-0.5 text-xs font-medium ${roleColor()}`}>
              <Shield className="h-3 w-3" />
              {roleLabel()}
            </span>
          </div>
        </div>

        <div className="space-y-3 border-t border-slate-100 pt-4">
          <div className="flex items-center gap-3">
            <Mail className="h-4 w-4 text-slate-400" />
            <div>
              <p className="text-xs text-slate-500">E-mail</p>
              <p className="text-sm font-medium text-slate-900">{user.email}</p>
            </div>
          </div>
          <div className="flex items-center gap-3">
            <FileText className="h-4 w-4 text-slate-400" />
            <div>
              <p className="text-xs text-slate-500">{isSeller ? "CNPJ" : "CPF"}</p>
              <p className="text-sm font-medium text-slate-900">{formatDoc(user.document)}</p>
            </div>
          </div>
        </div>
      </div>

      {/* Quick links */}
      <div className="rounded-2xl border border-slate-200 bg-white divide-y divide-slate-100">
        <button
          onClick={() => router.push("/orders")}
          className="flex w-full items-center justify-between p-4 text-left hover:bg-slate-50"
        >
          <span className="text-sm font-medium text-slate-700">Meus Pedidos</span>
          <span className="text-xs text-slate-400">&rarr;</span>
        </button>
        <button
          onClick={() => router.push("/wallet")}
          className="flex w-full items-center justify-between p-4 text-left hover:bg-slate-50"
        >
          <span className="text-sm font-medium text-slate-700">Minha Carteira</span>
          <span className="text-xs text-slate-400">&rarr;</span>
        </button>
      </div>

      <Button onClick={handleLogout} variant="danger" className="w-full">
        <LogOut className="h-4 w-4" /> Sair da Conta
      </Button>
    </div>
  );
}
