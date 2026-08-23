import { computed, Injectable, signal } from '@angular/core';
import type { MeResponse } from '../../features/auth/login/login.models';

const STORAGE_KEY = 'benteveo_token';

export type SessionUser = MeResponse;

@Injectable({ providedIn: 'root' })
export class SessionService {
  private readonly tokenSignal = signal<string | null>(localStorage.getItem(STORAGE_KEY));
  private readonly userSignal = signal<SessionUser | null>(null);

  readonly token = this.tokenSignal.asReadonly();
  readonly user = this.userSignal.asReadonly();
  readonly isAuthenticated = computed(() => this.tokenSignal() !== null);

  setSession(token: string): void {
    localStorage.setItem(STORAGE_KEY, token);
    this.tokenSignal.set(token);
  }

  setUser(user: SessionUser): void {
    this.userSignal.set(user);
    console.log(user);

  }

  clearSession(): void {
    localStorage.removeItem(STORAGE_KEY);
    this.tokenSignal.set(null);
    this.userSignal.set(null);
  }
}
