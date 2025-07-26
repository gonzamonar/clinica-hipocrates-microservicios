import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { DataTurnosService } from '../../services/data-turnos.service';
import { CommonModule, DatePipe } from '@angular/common';
import { Turno } from '../../models/turno';
import { DataUsuariosService } from '../../services/data-usuarios.service';
import { Especialista } from '../../models/especialista';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { FormsModule } from '@angular/forms';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { PdfMakerService } from '../../services/pdf-maker.service';
import { MatTabsModule } from '@angular/material/tabs';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFilePdf, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { Estado } from '../../models/enums/estado';

@Component({
  selector: 'app-info-estadisticas',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatChipsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    NgxChartsModule,
    MatTabsModule,
    FontAwesomeModule,
    MatButtonModule,
    MatDividerModule
  ],
  templateUrl: './info-estadisticas.component.html',
  styleUrl: './info-estadisticas.component.css'
})

export class InfoEstadisticasComponent implements OnInit {
  dataEspecialidades!: any[];
  dataDias!: any[];
  dataSolicitados!: any[];
  dataFinalizados!: any[];

  iconPdf: IconDefinition = faFilePdf;
  height: number = 0;
  printingPdf: boolean = false;
  fecha!: Date;

  turnos!: Turno[];
  turnosSolicitados!: Turno[];
  turnosRealizados!: Turno[];
  especialidades!: string[];
  especialistas!: Especialista[];
  emailsEspecialistas!: string[];
  dias!: string[];
  fechaTurnosSolicitadosDesde!: Date;
  fechaTurnosSolicitadosHasta!: Date;
  fechaTurnosRealizadosDesde!: Date;
  fechaTurnosRealizadosHasta!: Date;
  
  constructor(
    private providerDataTurnos: DataTurnosService,
    private providerDataUsuarios: DataUsuariosService,
    private pdfMaker: PdfMakerService
  ){}

  @ViewChild('pdfEspecialidad') pdfEspecialidad!: ElementRef;
  @ViewChild('pdfFecha') pdfFecha!: ElementRef;
  @ViewChild('pdfTurnosSolicitados') pdfTurnosSolicitados!: ElementRef;
  @ViewChild('pdfTurnosFinalizados') pdfTurnosFinalizados!: ElementRef;

  ngOnInit() {
    this.providerDataTurnos.fetchAll().subscribe(
      (res) => {
        this.turnos = Turno.constructorArr(res);
        this.turnosSolicitados = this.turnos;
        this.turnosRealizados = this.turnos;
        this.especialidades = Turno.getEspecialidades(Turno.ordenarPorEspecialidad(this.turnos));
        this.emailsEspecialistas = Turno.getEspecialistas(Turno.ordenarPorEspecialista(this.turnos));
        this.dias = Turno.getDias(Turno.ordenarPorFecha(this.turnos));

        this.dataEspecialidades = this.especialidades.map((e) => { return {'name': e, 'value': this.cantidadTurnosEspecialidades(e)}; })
        this.dataDias = this.dias.map((d) => { return {'name': d , 'value': this.cantidadTurnosDias(d) } ;} );
    });

    this.providerDataUsuarios.fetchAll().subscribe(
      (res) => {
        this.especialistas = Especialista.filtrarPorEmails(Especialista.constructorArr(res), this.emailsEspecialistas);
        this.dataSolicitados = this.especialistas.map((e: Especialista) => { return {'name': e.fullName() , 'value': this.cantidadTurnosSolicitadosEspecialistas(e.email) } ;} );
        this.dataFinalizados = this.especialistas.map((e: Especialista) => { return {'name': e.fullName() , 'value': this.cantidadTurnosFinalizadosEspecialistas(e.email) } ;} );
    });
  }

  cantidadTurnosEspecialidades(especialidad: string){
    return Turno.contarPorEspecialidad(this.turnos, especialidad);
  }

  cantidadTurnosDias(dia: string){
    return Turno.contarPorDia(this.turnos, dia);
  }

  cantidadTurnosSolicitadosEspecialistas(especialista: string){
    return Turno.contarPorNoEstado(Turno.filtrarPorEspecialista(this.turnosSolicitados, especialista), Estado.Realizado);
  }
  
  cantidadTurnosFinalizadosEspecialistas(especialista: string){
    return Turno.contarPorEstado(Turno.filtrarPorEspecialista(this.turnosRealizados, especialista), Estado.Realizado);
  }

  filtrarFechaTurnosSolicitados(){
    if (this.fechaTurnosSolicitadosDesde != null && this.fechaTurnosSolicitadosHasta != null){
      let desde = this.formatDate(this.fechaTurnosSolicitadosDesde);
      let hasta = this.formatDate(this.fechaTurnosSolicitadosHasta);
      this.turnosSolicitados = Turno.filtrarPorFecha(this.turnos, desde, hasta);
      this.dataSolicitados = this.especialistas.map((e: Especialista) => { return {'name': e.fullName() , 'value': this.cantidadTurnosSolicitadosEspecialistas(e.email) } ;} );
    }
  }

  filtrarFechaTurnosRealizados(){
    if (this.fechaTurnosRealizadosDesde != null && this.fechaTurnosRealizadosHasta != null){
      let desde = this.formatDate(this.fechaTurnosRealizadosDesde);
      let hasta = this.formatDate(this.fechaTurnosRealizadosHasta);
      this.turnosRealizados = Turno.filtrarPorFecha(this.turnos, desde, hasta);
      this.dataFinalizados = this.especialistas.map((e: Especialista) => { return {'name': e.fullName() , 'value': this.cantidadTurnosFinalizadosEspecialistas(e.email) } ;} );
    }
  }

  formatDate(date: Date): string {
    let day: string = this.addZeroes(date.getDate().toString());
    let month: string = this.addZeroes((date.getMonth() + 1).toString());
    let year: string = date.getFullYear().toString();
    return year + "-" + month + "-" + day;
  }

  private addZeroes(str: string){
    return str.length == 1 ? "0" + str : str ;
  }

  descargarPDF_PorEspecialidad(){
    this.descargarPDF_Timeout(this.pdfEspecialidad.nativeElement, "estadísticas_por_especialidad");
  }

  descargarPDF_PorDia(){
    this.descargarPDF_Timeout(this.pdfFecha.nativeElement, "estadísticas_por_dia");
  }

  descargarPDF_Solicitados(){
    this.descargarPDF_Timeout(this.pdfTurnosSolicitados.nativeElement, "estadísticas_por_turnos_solicitados");
  }

  descargarPDF_Finalizados(){
    this.descargarPDF_Timeout(this.pdfTurnosFinalizados.nativeElement, "estadísticas_por_turnos_realizados");
  }

  descargarPDF_Timeout(element: HTMLElement, filename: string){
    this.printingPdf = true;
    this.height = 150;
    this.fecha = new Date();

    setTimeout(() => {
      this.pdfMaker.createPDF(element, filename);
    }, 50);

    setTimeout(() => {
      this.printingPdf = false;
      this.height = 0;
    }, 100);
  }
}
