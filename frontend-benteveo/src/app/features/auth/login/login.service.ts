import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiClientService } from '../../../core/services/api-client.service';
import { LoginRequest, LoginResponse, MeResponse } from './login.models';

@Injectable({ providedIn: 'root' })
export class LoginService {
  private readonly apiClient = inject(ApiClientService);

  login(request: LoginRequest): Observable<LoginResponse> {
    return this.apiClient.post<LoginResponse>('/auth/login', request);
  }

  me(): Observable<MeResponse> {
    return this.apiClient.get<MeResponse>('/auth/me');
  }
}
