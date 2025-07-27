import axios, { AxiosInstance } from 'axios';

export function createClient(token: string): AxiosInstance {
  return axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}