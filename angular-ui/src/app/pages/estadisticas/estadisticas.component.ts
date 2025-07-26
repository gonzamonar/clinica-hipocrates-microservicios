import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { ListadoLogsComponent } from '../../components/listado-logs/listado-logs.component';
import { SessionService } from '../../services/session.service';
import { InfoEstadisticasComponent } from '../../components/info-estadisticas/info-estadisticas.component';

@Component({
  selector: 'app-estadisticas',
  standalone: true,
  imports: [
    CommonModule,
    MatTabsModule,
    ListadoLogsComponent,
    InfoEstadisticasComponent
  ],
  templateUrl: './estadisticas.component.html',
  styleUrl: './estadisticas.component.css'
})

export class EstadisticasComponent {

  constructor(
    public session: SessionService
  ){ }
}
