import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiClientService } from '../../core/services/api-client.service';
import { Product } from '../../shared/models/product';

@Injectable({ providedIn: 'root' })
export class HomeService {
  private readonly apiClient = inject(ApiClientService);

  getProducts(): Observable<Product[]> {
    return this.apiClient.get<Product[]>('/products');
  }
}
