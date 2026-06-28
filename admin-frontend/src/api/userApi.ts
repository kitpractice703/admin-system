import axios from "axios";

const BASE_URL = import.meta.env.VITE_USER_URL;

export interface UserResponse {
  id: number;
  username: string;
  email: string;
  phoneNumber: string;
  createdAt: string;
}

export interface UserRequest {
  username: string;
  email: string;
  phoneNumber: string;
}

export const getUsers = () =>
  axios.get<UserResponse[]>(`${BASE_URL}/api/users`);

export const getUserById = (id: number) =>
  axios.get<UserResponse>(`${BASE_URL}/api/users/${id}`);

export const createUser = (data: UserRequest) =>
  axios.post<UserResponse>(`${BASE_URL}/api/users`, data);

export const updateUser = (id: number, data: UserRequest) =>
  axios.put<UserResponse>(`${BASE_URL}/api/users/${id}`, data);

export const deleteUser = (id: number) =>
  axios.delete(`${BASE_URL}/api/users/${id}`);
