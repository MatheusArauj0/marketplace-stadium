"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { useAuth } from "@/contexts/AuthContext";
import Button from "@/components/ui/Button";
import Input from "@/components/ui/Input";
import toast from "react-hot-toast";

export default function RegisterPage() {
  const router = useRouter();
  const { register, registerSeller } = useAuth();
  const [tab, setTab] = useState<"customer" | "seller">("customer");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [document, setDocument] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      const payload = { name, email, password, document };
      if (tab === "seller") {
        await registerSeller(payload);
      } else {
        await register(payload);
      }
      toast.success("Conta criada com sucesso!");
      router.push("/catalog");
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Erro ao criar conta");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-[70dvh] flex-col items-center justify-center">
      <div className="w-full max-w-sm space-y-6">
        <div className="text-center">
          <h1 className="text-2xl font-bold text-slate-900">Criar Conta</h1>
          <p className="mt-1 text-sm text-slate-500">Cadastre-se para começar a pedir</p>
        </div>

        {/* Tabs */}
        <div className="flex rounded-xl bg-slate-100 p-1">
          <button
            onClick={() => setTab("customer")}
            className={`flex-1 rounded-lg py-2 text-sm font-semibold transition-all ${
              tab === "customer"
                ? "bg-white text-primary-600 shadow-sm"
                : "text-slate-500 hover:text-slate-700"
            }`}
          >
            Torcedor
          </button>
          <button
            onClick={() => setTab("seller")}
            className={`flex-1 rounded-lg py-2 text-sm font-semibold transition-all ${
              tab === "seller"
                ? "bg-white text-primary-600 shadow-sm"
                : "text-slate-500 hover:text-slate-700"
            }`}
          >
            Lojista
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            id="name"
            label={tab === "seller" ? "Nome da Loja" : "Nome Completo"}
            placeholder={tab === "seller" ? "Lanchonete do Estádio" : "João da Silva"}
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
          <Input
            id="email"
            label="E-mail"
            type="email"
            placeholder="seu@email.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <Input
            id="document"
            label={tab === "seller" ? "CNPJ" : "CPF"}
            placeholder={tab === "seller" ? "00.000.000/0001-00" : "000.000.000-00"}
            value={document}
            onChange={(e) => setDocument(e.target.value.replace(/\D/g, ""))}
            maxLength={tab === "seller" ? 14 : 11}
            inputMode="numeric"
            required
          />
          <Input
            id="password"
            label="Senha"
            type="password"
            placeholder="Mínimo 6 caracteres"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            minLength={6}
            required
          />
          <Button type="submit" loading={loading} className="w-full" size="lg">
            {tab === "seller" ? "Cadastrar Loja" : "Cadastrar"}
          </Button>
        </form>

        <div className="text-center text-sm text-slate-500">
          Já tem conta?{" "}
          <Link href="/login" className="font-semibold text-primary-600 hover:underline">
            Entrar
          </Link>
        </div>
      </div>
    </div>
  );
}
