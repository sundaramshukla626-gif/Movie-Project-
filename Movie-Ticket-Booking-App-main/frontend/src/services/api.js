
import axios from 'axios';

export const API = axios.create({
  baseURL: 'http://localhost:8080/api',
  withCredentials: true,   //  important for CORS + cookies
});

API.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers['X-Auth-Token'] = token;
  }
  return config;
});
