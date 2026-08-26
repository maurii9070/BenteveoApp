import { Component } from '@angular/core';

@Component({
  selector: 'app-profile',
  imports: [],
  template: `
    <section class="bg-surface">
      <div class="mx-auto max-w-3xl px-6 py-16 md:py-24">
        <h1 class="text-3xl font-bold tracking-tighter text-brand md:text-4xl">Mi Perfil</h1>
        <p class="mt-3 max-w-[65ch] text-brand-muted">
          La sección de perfil se completa en una próxima iteración.
        </p>
      </div>
    </section>
  `,
})
export class ProfileComponent {}