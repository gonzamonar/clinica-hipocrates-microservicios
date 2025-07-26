import { Component } from '@angular/core';
import { ListadoTurnosComponent } from '../../components/listado-turnos/listado-turnos.component';
import { MatTabsModule } from '@angular/material/tabs';
import { SessionService } from '../../services/session.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-mis-turnos',
  standalone: true,
  imports: [
    CommonModule,
    ListadoTurnosComponent,
    MatTabsModule
  ],
  templateUrl: './mis-turnos.component.html',
  styleUrl: './mis-turnos.component.css'
})
export class MisTurnosComponent {

  constructor(
    public session: SessionService,
  ) { }
}
