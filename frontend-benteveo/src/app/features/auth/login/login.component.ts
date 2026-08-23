import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { form, FormField, submit, required, email, minLength } from '@angular/forms/signals';
import { firstValueFrom } from 'rxjs';
import { toast } from 'ngx-sonner';

import { SessionService } from '../../../core/services/session.service';
import { LoginRequest } from './login.models';
import { LoginService } from './login.service';

@Component({
  selector: 'app-login',
  imports: [RouterLink, FormField],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  private readonly loginService = inject(LoginService);
  private readonly sessionService = inject(SessionService);
  private readonly router = inject(Router);

  protected readonly isSubmitting = signal(false);

  protected readonly model = signal({
    email: '',
    password: '',
  });

  protected readonly loginForm = form(this.model, (s) => {
    required(s.email, { message: 'El email es obligatorio.' });
    email(s.email, { message: 'Ingresá un email válido.' });
    required(s.password, { message: 'La contraseña es obligatoria.' });
    minLength(s.password, 6, { message: 'Mínimo 6 caracteres.' });
  });

  onSubmit(): void {
    submit(this.loginForm, async () => {
      this.isSubmitting.set(true);

      const { email, password } = this.model();
      const request: LoginRequest = { email, password };

      try {
        const { token } = await firstValueFrom(this.loginService.login(request));
        this.sessionService.setSession(token);

        const me = await firstValueFrom(this.loginService.me());
        this.sessionService.setUser(me);

        toast.success('¡Iniciaste sesión correctamente!');
        await this.router.navigate(['/']);
      } finally {
        this.isSubmitting.set(false);
      }
    });
  }
}
