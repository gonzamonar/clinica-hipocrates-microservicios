import { Component } from '@angular/core';
import { ListadoTurnosComponent } from '../../components/listado-turnos/listado-turnos.component';
import { MatTabsModule } from '@angular/material/tabs';
import { SessionService } from '../../services/session.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-turnos',
  standalone: true,
  imports: [
    CommonModule,
    ListadoTurnosComponent,
    MatTabsModule
  ],
  templateUrl: './turnos.component.html',
  styleUrl: './turnos.component.css'
})

export class TurnosComponent {

  constructor(
    public session: SessionService,
  ) { }
}
