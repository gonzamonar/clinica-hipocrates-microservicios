import { Component } from '@angular/core';

@Component({
  selector: 'app-instalaciones',
  standalone: true,
  imports: [],
  templateUrl: './instalaciones.component.html',
  styleUrl: './instalaciones.component.css'
})
export class InstalacionesComponent {
  watermark: string = "../../../assets/img/navbar/logo.png";
  watermarkAlt: string = "Clínica Hipócrates";
}
