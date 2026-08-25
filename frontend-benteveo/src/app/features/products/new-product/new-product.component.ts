import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { form, FormField, min, required, submit } from '@angular/forms/signals';
import { firstValueFrom } from 'rxjs';
import { toast } from 'ngx-sonner';

import { Category } from '../../../shared/models/category';
import { CreateProductRequest } from './new-product.models';
import { NewProductService } from './new-product.service';

@Component({
  selector: 'app-new-product',
  imports: [RouterLink, FormField],
  templateUrl: './new-product.component.html',
})
export class NewProductComponent implements OnInit {
  private readonly newProductService = inject(NewProductService);
  private readonly router = inject(Router);

  protected readonly isSubmitting = signal(false);
  protected readonly categories = signal<Category[]>([]);
  protected readonly selectedPhotos = signal<File[]>([]);
  protected readonly photoPreviews = signal<string[]>([]);
  protected readonly currentStep = signal<1 | 2>(1);

  protected readonly model = signal({
    title: '',
    description: '',
    categoryId: '',
    priceDay: null as number | null,
    priceWeek: null as number | null,
    priceMonth: null as number | null,
    deposit: null as number | null,
  });

  protected readonly newProductForm = form(this.model, (s) => {
    required(s.title, { message: 'El título es obligatorio.' });
    required(s.description, { message: 'La descripción es obligatoria.' });
    required(s.categoryId, { message: 'Elegí una categoría.' });
    required(s.priceDay, { message: 'El precio diario es obligatorio.' });
    required(s.priceMonth, { message: 'El precio mensual es obligatorio.' });
    required(s.deposit, { message: 'La seña es obligatoria.' });
    min(s.priceDay, 0, { message: 'El precio diario no puede ser negativo.' });
    min(s.priceWeek, 0, { message: 'El precio semanal no puede ser negativo.' });
    min(s.priceMonth, 0, { message: 'El precio mensual no puede ser negativo.' });
    min(s.deposit, 0, { message: 'La seña no puede ser negativa.' });
  });

  ngOnInit(): void {
    this.newProductService.getCategories().subscribe({
      next: (values) => this.categories.set(values),
      error: (err: Error) => toast.error(err.message),
    });
  }

  protected onFilesSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const files = Array.from(input.files ?? []);

    // Revocar previews anteriores para liberar memoria antes de reemplazarlas.
    this.photoPreviews().forEach((url) => URL.revokeObjectURL(url));
    this.photoPreviews.set(files.map((file) => URL.createObjectURL(file)));
    this.selectedPhotos.set(files);

    // Permitir volver a elegir los mismos archivos.
    input.value = '';
  }

  protected onRemovePhoto(index: number): void {
    const urls = this.photoPreviews();
    URL.revokeObjectURL(urls[index]);
    this.photoPreviews.set(urls.filter((_, i) => i !== index));
    this.selectedPhotos.set(this.selectedPhotos().filter((_, i) => i !== index));
  }

  protected onContinue(): void {
    // submit valida el formulario y solo ejecuta la lógica si es válido,
    // además de marcar los campos como touched para mostrar los errores.
    submit(this.newProductForm, async () => {
      this.currentStep.set(2);
    });
  }

  protected onBack(): void {
    this.currentStep.set(1);
  }

  protected onSubmit(): void {
    submit(this.newProductForm, async () => {
      this.isSubmitting.set(true);

      const { title, description, categoryId, priceDay, priceWeek, priceMonth, deposit } =
        this.model();
      const request: CreateProductRequest = {
        title,
        description,
        categoryId: Number(categoryId),
        priceDay: priceDay!,
        priceWeek,
        priceMonth: priceMonth!,
        deposit: deposit!,
      };

      try {
        const created = await firstValueFrom(this.newProductService.create(request));

        if (this.selectedPhotos().length) {
          await firstValueFrom(this.newProductService.addPhotos(created.id, this.selectedPhotos()));
        }

        toast.success('¡Producto publicado!');
        await this.router.navigate(['/']);
      } finally {
        this.isSubmitting.set(false);
      }
    });
  }
}
