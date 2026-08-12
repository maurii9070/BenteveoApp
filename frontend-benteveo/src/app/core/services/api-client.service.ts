import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { ApiError, ApiResponse } from '../../shared/models/api-response';

interface ApiClientOptions {
  params?: HttpParams | Record<string, string | number | boolean | readonly (string | number | boolean)[]>;
  headers?: Record<string, string>;
}

@Injectable({ providedIn: 'root' })
export class ApiClientService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiUrl;

  get<T>(path: string, options?: ApiClientOptions): Observable<T> {
    return this.request<T>('GET', path, options);
  }

  post<T>(path: string, body?: unknown, options?: ApiClientOptions): Observable<T> {
    return this.request<T>('POST', path, options, body);
  }

  put<T>(path: string, body?: unknown, options?: ApiClientOptions): Observable<T> {
    return this.request<T>('PUT', path, options, body);
  }

  patch<T>(path: string, body?: unknown, options?: ApiClientOptions): Observable<T> {
    return this.request<T>('PATCH', path, options, body);
  }

  delete<T>(path: string, options?: ApiClientOptions): Observable<T> {
    return this.request<T>('DELETE', path, options);
  }

  private request<T>(
    method: string,
    path: string,
    options?: ApiClientOptions,
    body?: unknown,
  ): Observable<T> {
    return this.http
      .request<ApiResponse<T>>(method, `${this.baseUrl}${path}`, {
        ...options,
        body,
      })
      .pipe(
        map((response) => {
          if (!response.success) {
            throw new ApiError(response.message);
          }
          return response.data;
        }),
      );
  }
}
