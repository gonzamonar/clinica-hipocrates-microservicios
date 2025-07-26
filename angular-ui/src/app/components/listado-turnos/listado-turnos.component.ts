import { AfterContentChecked, Component, OnInit } from '@angular/core';
import { Turno } from '../../models/turno';
import { CommonModule } from '@angular/common';
import { CardTurnoComponent } from '../card-turno/card-turno.component';
import { SessionService } from '../../services/session.service';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { DatabaseService } from '../../services/database.service';
import { ToDatePipe } from '../../pipes/to-date.pipe';
import { ToTimePipe } from '../../pipes/to-time.pipe';

@Component({
  selector: 'app-listado-turnos',
  standalone: true,
  imports: [
    CommonModule,
    CardTurnoComponent,
    FormsModule,
    MatInputModule,
    MatIconModule
  ],
  templateUrl: './listado-turnos.component.html',
  styleUrl: './listado-turnos.component.css'
})

export class ListadoTurnosComponent implements AfterContentChecked {
  turnos: Turno[] = [];
  turnosFiltrados: Turno[] = [];
  searchStr: string = '';

  constructor(
    private session: SessionService,
    private DB: DatabaseService,
    private DatePipe: ToDatePipe,
    private TimePipe: ToTimePipe
  ){ }

  ngAfterContentChecked(): void {
    this.turnos = Turno.ordenarPorNroTurnoDesc(this.DB.turnos);
    this.filtrarTurnos();
  }

  filtrarTurnos(): void {
    let searchStr = this.searchStr.toLocaleLowerCase();

    if (this.session.isPatientLevelSession() && this.session.data != null){
      this.turnos = Turno.filtrarPorPaciente(this.turnos, this.session.data?.email);
    }

    if (this.session.isSpecialistLevelSession() && this.session.data != null){
      this.turnos = Turno.filtrarPorEspecialista(this.turnos, this.session.data?.email);
    }

    this.turnosFiltrados = this.turnos;

    if (searchStr != ''){
      this.turnosFiltrados = this.turnosFiltrados.filter((t) => {
        return t.includes(searchStr)
              || this.DatePipe.transform(t.dia).includes(searchStr)
              || this.TimePipe.transform(t.hora).includes(searchStr);
      });
    }
  }
}
