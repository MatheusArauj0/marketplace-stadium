// ====== Auth ======
export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  document: string;
}

export interface TokenResponse {
  accessToken: string;
  refreshToken: string;
}

export interface User {
  id: string;
  name: string;
  email: string;
  document: string;
  roles: string[];
}

// ====== Catalog ======
export interface Category {
  id: string;
  name: string;
  createdAt: string;
}

export interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  imgUrl: string;
  quantidade: number;
  date: string;
  categories: Category[];
}

// ====== Cart ======
export interface AddCartItemRequest {
  productId: string;
  quantity: number;
}

export interface UpdateCartItemRequest {
  quantity: number;
}

export interface CartItem {
  id: string;
  productId: string;
  productName: string;
  productImgUrl: string;
  unitPrice: number;
  quantity: number;
  subtotal: number;
}

// ====== Order ======
export interface CheckoutRequest {
  paymentMethod: string;
}

export interface CheckoutResponse {
  orderId: string;
  pickupCode: string;
  total: number;
  status: string;
}

export interface OrderSummary {
  orderId: string;
  total: number;
  status: string;
  pickupCode: string;
  itemCount: number;
  itemsSummary: string;
  createdAt: string;
}

export interface OrderItem {
  productId: string;
  productName: string;
  productImgUrl: string;
  quantity: number;
  unitPrice: number;
  subtotal: number;
}

export interface OrderDetail {
  orderId: string;
  total: number;
  status: string;
  pickupCode: string;
  paymentMethod: string;
  paymentStatus: string;
  items: OrderItem[];
  createdAt: string;
}

// ====== Wallet ======
export interface Wallet {
  id: string;
  userId: string;
  balance: number;
}

export interface CreditWalletRequest {
  amount: number;
}

export interface Transaction {
  id: string;
  type: string;
  amount: number;
  description: string;
  referenceId: string | null;
  createdAt: string;
}

// ====== Pagination ======
export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}
