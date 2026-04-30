"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
  useCallback,
  type ReactNode,
} from "react";
import api from "@/lib/api";
import type { User, LoginRequest, RegisterRequest, TokenResponse } from "@/types";

interface AuthContextType {
  user: User | null;
  loading: boolean;
  login: (data: LoginRequest) => Promise<void>;
  register: (data: RegisterRequest) => Promise<void>;
  registerSeller: (data: RegisterRequest) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
  isSeller: boolean;
  isAdmin: boolean;
}

const AuthContext = createContext<AuthContextType>({} as AuthContextType);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  const fetchUser = useCallback(async () => {
    try {
      const token = localStorage.getItem("accessToken");
      if (!token) {
        setLoading(false);
        return;
      }
      const { data } = await api.get<User>("/api/v1/users/me");
      setUser(data);
    } catch {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      setUser(null);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchUser();
  }, [fetchUser]);

  const saveTokens = (tokens: TokenResponse) => {
    localStorage.setItem("accessToken", tokens.accessToken);
    localStorage.setItem("refreshToken", tokens.refreshToken);
  };

  const login = async (credentials: LoginRequest) => {
    const { data } = await api.post<TokenResponse>("/api/v1/auth/login", credentials);
    saveTokens(data);
    await fetchUser();
  };

  const register = async (payload: RegisterRequest) => {
    const { data } = await api.post<TokenResponse>("/api/v1/auth/register", payload);
    saveTokens(data);
    await fetchUser();
  };

  const registerSeller = async (payload: RegisterRequest) => {
    const { data } = await api.post<TokenResponse>("/api/v1/auth/register/seller", payload);
    saveTokens(data);
    await fetchUser();
  };

  const logout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        loading,
        login,
        register,
        registerSeller,
        logout,
        isAuthenticated: !!user,
        isSeller: user?.roles?.includes("ROLE_SELLER") ?? false,
        isAdmin: user?.roles?.includes("ROLE_ADMIN") ?? false,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);
