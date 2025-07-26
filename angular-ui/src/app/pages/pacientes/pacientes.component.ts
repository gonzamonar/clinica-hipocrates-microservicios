import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { ListadoHistoriaClinicaComponent } from '../../components/listado-historia-clinica/listado-historia-clinica.component';
import { SessionService } from '../../services/session.service';
import { ListadoPacientesComponent } from "../../components/listado-pacientes/listado-pacientes.component";

@Component({
  selector: 'app-pacientes',
  standalone: true,
  imports: [
    CommonModule,
    MatTabsModule,
    ListadoHistoriaClinicaComponent,
    ListadoPacientesComponent,
],
  templateUrl: './pacientes.component.html',
  styleUrl: './pacientes.component.css'
})
export class PacientesComponent {

  constructor(
    public session: SessionService,
  ) { }
}
