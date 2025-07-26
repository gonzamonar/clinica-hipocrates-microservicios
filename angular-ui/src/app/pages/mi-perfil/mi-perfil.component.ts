import { Component } from '@angular/core';
import { HorariosComponent } from '../../components/horarios/horarios.component';
import { MatTabsModule } from '@angular/material/tabs';
import { PerfilComponent } from '../../components/perfil/perfil.component';
import { SessionService } from '../../services/session.service';
import { CommonModule } from '@angular/common';
import { ListadoHistoriaClinicaComponent } from '../../components/listado-historia-clinica/listado-historia-clinica.component';

@Component({
  selector: 'app-mi-perfil',
  standalone: true,
  imports: [
    CommonModule,
    HorariosComponent,
    PerfilComponent,
    MatTabsModule,
    ListadoHistoriaClinicaComponent,
  ],
  templateUrl: './mi-perfil.component.html',
  styleUrl: './mi-perfil.component.css'
})
export class MiPerfilComponent {

  constructor(
    public session: SessionService
  ){ }
}
