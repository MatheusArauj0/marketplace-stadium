"use client";

import { createContext, useContext, useState, useCallback, type ReactNode } from "react";
import api from "@/lib/api";
import type { CartItem, Page } from "@/types";

interface CartContextType {
  items: CartItem[];
  itemCount: number;
  total: number;
  loading: boolean;
  fetchCart: () => Promise<void>;
  addItem: (productId: string, quantity: number) => Promise<void>;
  updateItem: (itemId: string, quantity: number) => Promise<void>;
  removeItem: (itemId: string) => Promise<void>;
  clearCart: (cartId: string) => Promise<void>;
}

const CartContext = createContext<CartContextType>({} as CartContextType);

export function CartProvider({ children }: { children: ReactNode }) {
  const [items, setItems] = useState<CartItem[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchCart = useCallback(async () => {
    setLoading(true);
    try {
      const { data } = await api.get<Page<CartItem>>("/api/v1/carts?size=100");
      setItems(data.content);
    } catch {
      setItems([]);
    } finally {
      setLoading(false);
    }
  }, []);

  const addItem = async (productId: string, quantity: number) => {
    await api.post("/api/v1/carts/items", [{ productId, quantity }]);
    await fetchCart();
  };

  const updateItem = async (itemId: string, quantity: number) => {
    await api.patch(`/api/v1/carts/items/${itemId}`, { quantity });
    await fetchCart();
  };

  const removeItem = async (itemId: string) => {
    await api.delete(`/api/v1/carts/items/${itemId}`);
    await fetchCart();
  };

  const clearCart = async (cartId: string) => {
    await api.delete(`/api/v1/carts/${cartId}`);
    setItems([]);
  };

  const itemCount = items.reduce((sum, item) => sum + item.quantity, 0);
  const total = items.reduce((sum, item) => sum + item.subtotal, 0);

  return (
    <CartContext.Provider
      value={{ items, itemCount, total, loading, fetchCart, addItem, updateItem, removeItem, clearCart }}
    >
      {children}
    </CartContext.Provider>
  );
}

export const useCart = () => useContext(CartContext);
