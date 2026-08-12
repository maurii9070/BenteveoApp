import { Component, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import {
  form,
  FormField,
  submit,
  required,
  email,
  minLength,
  validate,
} from '@angular/forms/signals';

@Component({
  selector: 'app-register',
  imports: [RouterLink, FormField],
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  protected readonly model = signal({
    firstName: '',
    lastName: '',
    email: '',
    dni: '',
    password: '',
    confirmPassword: '',
  });

  protected readonly registerForm = form(this.model, (s) => {
    required(s.firstName, { message: 'Nombre obligatorio.' });
    required(s.lastName, { message: 'Apellido obligatorio.' });
    required(s.email, { message: 'El email es obligatorio.' });
    email(s.email, { message: 'Ingresá un email válido.' });
    required(s.dni, { message: 'El DNI es obligatorio.' });
    required(s.password, { message: 'La contraseña es obligatoria.' });
    minLength(s.password, 6, { message: 'Mínimo 6 caracteres.' });
    required(s.confirmPassword, { message: 'Confirmá tu contraseña.' });
    validate(s.confirmPassword, ({ valueOf }) => {
      if (valueOf(s.confirmPassword) !== valueOf(s.password)) {
        return { kind: 'mismatch', message: 'Las contraseñas no coinciden.' };
      }
      return undefined;
    });
  });

  onSubmit(): void {
    submit(this.registerForm, async () => {
      const { firstName, lastName, email, dni, password } = this.model();
      console.log({ firstName, lastName, email, dni, password });
    });
  }
}