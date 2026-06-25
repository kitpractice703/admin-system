import axios from "axios";

const BASE_URL = import.meta.env.VITE_AUTH_URL;

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  email: string;
}

export const login = (data: LoginRequest) =>
  axios.post<LoginResponse>(`${BASE_URL}/api/auth/login`, data);
