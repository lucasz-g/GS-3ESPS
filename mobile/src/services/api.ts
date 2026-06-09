import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { getMockResponse } from './mockApi';

/**
 * Cliente HTTP do app. Ele tenta usar o backend real primeiro e só usa
 * os dados mockados quando não recebe resposta do servidor.
 */
const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000,
});

api.interceptors.request.use(async (config: any) => {
  const token = await AsyncStorage.getItem('token');

  if (token) {
    config.headers = {
      ...config.headers,
      Authorization: `Bearer ${token}`,
      'X-User-Id': '1',
    };
  }

  return config;
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (!error.response && error.config) {
      const mockResponse = await getMockResponse(error.config);
      if (mockResponse) {
        return mockResponse;
      }
    }

    return Promise.reject(error);
  },
);

export default api;
