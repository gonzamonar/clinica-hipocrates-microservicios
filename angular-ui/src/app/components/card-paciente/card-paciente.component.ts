import { Component, ElementRef, inject, Input, ViewChild } from '@angular/core';
import { Paciente } from '../../models/paciente';
import { CommonModule } from '@angular/common';
import { MatTooltipModule } from '@angular/material/tooltip';
import { ToTitleCasePipe } from '../../pipes/to-title-case.pipe';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFileExcel, faImage, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { MatButtonModule } from '@angular/material/button';
import { Turno } from '../../models/turno';
import { DatabaseService } from '../../services/database.service';
import { CardTurnoComponent } from "../card-turno/card-turno.component";
import { ListadoTurnosTablaComponent } from '../listado-turnos-tabla/listado-turnos-tabla.component';
import { PdfHistoriaClinicaComponent } from '../pdf-historia-clinica/pdf-historia-clinica.component';
import { MatDialog } from '@angular/material/dialog';
import { HistoriaClinica } from '../../models/historia-clinica';
import { DataSource } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-card-paciente',
  standalone: true,
  imports: [
    CommonModule,
    MatTooltipModule,
    ToTitleCasePipe,
    FontAwesomeModule,
    MatButtonModule,
    ListadoTurnosTablaComponent,
],
  templateUrl: './card-paciente.component.html',
  styleUrl: './card-paciente.component.css'
})
export class CardPacienteComponent {
  @Input() paciente!: Paciente;
  readonly dialog = inject(MatDialog);
  iconXls: IconDefinition = faFileExcel;
  iconImg: IconDefinition = faImage;

  @ViewChild('profilePic') profilePic!: ElementRef;

  constructor (
    private DB: DatabaseService,
  ){ }

  getTurnos(){
    let turnos: Turno[] = [];
    if (this.paciente){
      turnos = Turno.filtrarPorPaciente(this.DB.turnos, this.paciente.email);
      while (turnos.length > 3) {
        turnos.shift();
      }
    }
    return turnos;
  }


  switchImgPerfil(){
    if (this.paciente) {
      let el = <HTMLImageElement> this.profilePic.nativeElement;
      el.src = el.src == this.paciente.imagenPerfil ? this.paciente.imagenPerfilAlt : this.paciente.imagenPerfil ;
    }
  }

  verHistoriaClinica() {
    let historiasClinicas = HistoriaClinica.filtrarPorPaciente(this.DB.historiasClinicas, this.paciente.email);

    this.dialog.open(PdfHistoriaClinicaComponent,
      { data : {
        dataSource: new MatTableDataSource<HistoriaClinica>(historiasClinicas),
        paciente: this.paciente,
        especialidad: '',
    }});
  }

}
