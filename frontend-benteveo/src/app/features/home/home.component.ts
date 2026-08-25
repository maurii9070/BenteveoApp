import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

import { Product } from '../../shared/models/product';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  private readonly homeService = inject(HomeService);

  protected readonly categories = [
    'Herramientas',
    'Autos',
    'Maquinaria',
    'Muebles',
    'Electrodomésticos',
    'Tecnología',
    'Bicicletas',
    'Deportes',
  ];

  protected readonly products = signal<Product[]>([]);
  protected readonly loading = signal(true);
  protected readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.homeService.getProducts().subscribe({
      next: (products) => {
        this.products.set(products);
        this.loading.set(false);
      },
      error: (err: Error) => {
        this.error.set(err.message);
        this.loading.set(false);
      },
    });
  }

  protected formatPrice(value: number): string {
    return new Intl.NumberFormat('es-AR', {
      style: 'currency',
      currency: 'ARS',
      maximumFractionDigits: 0,
    }).format(value);
  }

  protected primaryPhoto(product: Product): string | undefined {
    return (
      product.photos.find((photo) => photo.isPrimary)?.url ??
      product.photos[0]?.url
    );
  }
}
