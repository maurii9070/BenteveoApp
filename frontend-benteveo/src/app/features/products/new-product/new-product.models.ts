export interface CreateProductRequest {
  title: string;
  description: string;
  priceDay: number;
  priceWeek: number | null;
  priceMonth: number;
  deposit: number;
  categoryId: number;
}

export interface CreateProductResponse {
  id: string;
  title: string;
  status: string;
  createdAt: string;
}