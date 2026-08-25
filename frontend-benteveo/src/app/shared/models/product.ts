export interface PhotoResponse {
  id: number;
  url: string;
  publicId: string;
  caption: string | null;
  sortOrder: number;
  isPrimary: boolean;
}

export interface Product {
  id: string;
  title: string;
  description: string;
  priceDay: number;
  priceWeek: number | null;
  priceMonth: number;
  deposit: number;
  isActive: boolean;
  status: string;
  ratingAvg: number | null;
  ratingCount: number;
  categoryName: string;
  ownerId: string;
  photos: PhotoResponse[];
  createdAt: string;
  updatedAt: string;
}
