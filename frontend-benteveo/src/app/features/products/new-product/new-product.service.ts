import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiClientService } from '../../../core/services/api-client.service';
import { Category } from '../../../shared/models/category';
import { PhotoResponse } from '../../../shared/models/product';
import { CreateProductRequest, CreateProductResponse } from './new-product.models';

@Injectable({ providedIn: 'root' })
export class NewProductService {
  private readonly apiClient = inject(ApiClientService);

  getCategories(): Observable<Category[]> {
    return this.apiClient.get<Category[]>('/categories');
  }

  create(request: CreateProductRequest): Observable<CreateProductResponse> {
    return this.apiClient.post<CreateProductResponse>('/products', request);
  }

  addPhotos(productId: string, photos: File[]): Observable<PhotoResponse[]> {
    const formData = new FormData();
    photos.forEach((photo) => formData.append('files', photo));
    return this.apiClient.post<PhotoResponse[]>(`/products/${productId}/photos`, formData);
  }
}