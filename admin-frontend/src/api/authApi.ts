import axios from "axios";

const BASE_URL = "http://localhost:8081";

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
