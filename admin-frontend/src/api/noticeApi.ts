import axios from "axios";

const BASE_URL = import.meta.env.VITE_NOTICE_URL;

export interface NoticeResponse {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  updatedAt: string;
}

export interface NoticeRequest {
  title: string;
  content: string;
}

export const getNotices = () =>
  axios.get<NoticeResponse[]>(`${BASE_URL}/notices`);

export const getNoticeById = (id: number) =>
  axios.get<NoticeResponse>(`${BASE_URL}/notices/${id}`);

export const createNotice = (data: NoticeRequest) =>
  axios.post<NoticeResponse>(`${BASE_URL}/notices`, data);

export const updateNotice = (id: number, data: NoticeRequest) =>
  axios.put<NoticeResponse>(`${BASE_URL}/notices/${id}`, data);

export const deleteNotice = (id: number) =>
  axios.delete(`${BASE_URL}/notices/${id}`);
