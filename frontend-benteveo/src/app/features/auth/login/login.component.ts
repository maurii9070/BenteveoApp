import { Component, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { form, FormField, submit, required, email, minLength } from '@angular/forms/signals';

@Component({
  selector: 'app-login',
  imports: [RouterLink, FormField],
  templateUrl: './login.component.html',
})
export class LoginComponent {
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
      console.log(this.model());
    });
  }
}