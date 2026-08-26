import { Component, computed, inject, signal } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { toast } from 'ngx-sonner';

import { SessionService } from '../../../core/services/session.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  private readonly sessionService = inject(SessionService);
  private readonly router = inject(Router);

  protected readonly user = this.sessionService.user;
  protected readonly isAuthenticated = this.sessionService.isAuthenticated;

  protected readonly initials = computed(() => {
    const user = this.user();
    if (!user) {
      return '';
    }
    const first = user.firstName?.charAt(0) ?? '';
    const last = user.lastName?.charAt(0) ?? '';
    return `${first}${last}`.toUpperCase();
  });

  protected readonly displayName = computed(() => {
    const user = this.user();
    if (!user) {
      return '';
    }
    return [user.firstName, user.lastName].filter(Boolean).join(' ');
  });

  protected readonly menuOpen = signal(false);
  protected readonly mobileOpen = signal(false);

  toggleMenu(): void {
    this.menuOpen.update((open) => !open);
  }

  closeMenu(): void {
    this.menuOpen.set(false);
  }

  toggleMobile(): void {
    this.mobileOpen.update((open) => !open);
  }

  closeMobile(): void {
    this.mobileOpen.set(false);
  }

  onOverlayClick(): void {
    this.closeMenu();
    this.closeMobile();
  }

  logout(): void {
    this.sessionService.clearSession();
    this.closeMenu();
    this.closeMobile();
    toast.success('Cerraste sesión correctamente.');
    this.router.navigate(['/']);
  }
}