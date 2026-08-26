import { Routes } from '@angular/router';

import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'productos/nuevo',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./features/products/new-product/new-product.component').then(
        (m) => m.NewProductComponent,
      ),
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./features/auth/register/register.component').then((m) => m.RegisterComponent),
  },
  {
    path: 'perfil',
    loadComponent: () =>
      import('./features/profile/profile.component').then((m) => m.ProfileComponent),
  },
  {
    path: '',
    loadComponent: () =>
      import('./shared/components/layout/layout.component').then((m) => m.LayoutComponent),
    children: [
      {
        path: '',
        pathMatch: 'full',
        loadComponent: () => import('./features/home/home.component').then((m) => m.HomeComponent),
      },
      {
        path: 'reservations',
        loadComponent: () =>
          import('./features/reservations/reservations.component').then(
            (m) => m.ReservationsComponent,
          ),
      },
    ],
  },
  {
    path: '**',
    redirectTo: '',
  },
];
