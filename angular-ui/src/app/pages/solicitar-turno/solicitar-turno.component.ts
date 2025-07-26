import { Component } from '@angular/core';
import { FormSolicitudTurnoComponent } from '../../components/form-solicitud-turno/form-solicitud-turno.component';
import { MatTabsModule } from '@angular/material/tabs';
import { CommonModule } from '@angular/common';
import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-solicitar-turno',
  standalone: true,
  imports: [
    CommonModule,
    FormSolicitudTurnoComponent,
    MatTabsModule
  ],
  templateUrl: './solicitar-turno.component.html',
  styleUrl: './solicitar-turno.component.css'
})

export class SolicitarTurnoComponent {

  constructor(
    public session: SessionService,
  ) { }
}
