import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.component.html',
})
export class HomeComponent {
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
}
