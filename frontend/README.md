# Stadium Marketplace — Frontend

Frontend mobile-first em **Next.js 14 + TypeScript + Tailwind CSS** para o Stadium Marketplace.

## Setup

```bash
cd frontend
npm install
npm run dev
```

O app estará disponível em `http://localhost:3000`.

## Pré-requisitos

- Node.js 18+
- Backend rodando em `http://localhost:8080` (veja `backend/`)

## Variáveis de Ambiente

Crie um `.env.local` (já incluído):

```
NEXT_PUBLIC_API_URL=http://localhost:8080
```

## Estrutura

```
src/
├── app/            # Páginas (App Router)
│   ├── login/      # Tela de login
│   ├── register/   # Cadastro (torcedor/lojista)
│   ├── catalog/    # Cardápio de produtos
│   ├── cart/       # Carrinho de compras
│   ├── checkout/   # Finalização do pedido
│   ├── orders/     # Meus pedidos + QR Code
│   ├── wallet/     # Carteira digital
│   └── profile/    # Perfil do usuário
├── components/     # Componentes reutilizáveis
├── contexts/       # AuthContext, CartContext
├── lib/            # API client (Axios), utilitários
└── types/          # Tipagens TypeScript
```

## Credenciais de teste

| Usuário            | E-mail              | Senha  | Papel      |
|--------------------|---------------------|--------|------------|
| Carlos Torcedor    | carlos@email.com    | 123456 | CUSTOMER   |
| Maria Torcedora    | maria@email.com     | 123456 | CUSTOMER   |
| Loja Gol           | lojagol@email.com   | 123456 | SELLER     |
| Admin              | admin@email.com     | 123456 | ADMIN      |
