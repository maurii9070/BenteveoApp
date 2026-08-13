import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiClientService } from '../../../core/services/api-client.service';
import { RegisterRequest, RegisterResponse } from './register.models';

@Injectable({ providedIn: 'root' })
export class RegisterService {
  private readonly apiClient = inject(ApiClientService);

  register(request: RegisterRequest): Observable<RegisterResponse> {
    return this.apiClient.post<RegisterResponse>('/auth/register', request);
  }
}
