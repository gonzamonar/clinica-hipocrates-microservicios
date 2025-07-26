import { Component, inject, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RenderNullStringFieldPipe } from '../../pipes/render-null-string-field.pipe';
import { RenderBooleanFieldPipe } from '../../pipes/render-boolean-field.pipe';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { DataHistoriaClinicaService } from '../../services/data-historia-clinica.service';
import { SessionService } from '../../services/session.service';
import { HistoriaClinica } from '../../models/historia-clinica';
import { DatabaseService } from '../../services/database.service';
import { MatDialog } from '@angular/material/dialog';
import { PdfHistoriaClinicaComponent } from '../pdf-historia-clinica/pdf-historia-clinica.component';
import { Paciente } from '../../models/paciente';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { faFilePdf, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-listado-historia-clinica',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RenderNullStringFieldPipe,
    RenderBooleanFieldPipe,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatSortModule,
    MatSlideToggleModule,
    MatInputModule,
    MatSelectModule,
    FontAwesomeModule
  ],
  templateUrl: './listado-historia-clinica.component.html',
  styleUrl: './listado-historia-clinica.component.css'
})

export class ListadoHistoriaClinicaComponent {
  readonly dialog = inject(MatDialog);
  displayedColumns: string[] = ['fecha', 'paciente', 'especialista', 'altura', 'peso', 'temperatura', 'presion', 'datoDinamico1', 'datoDinamico2', 'datoDinamico3'];
  dataSource!: MatTableDataSource<HistoriaClinica>;
  title: string = 'Historias Clínicas';
  historiasClinicas: HistoriaClinica[] = [];
  especialidades: string[] = [];
  especialidad: string = '';
  iconPdf: IconDefinition = faFilePdf;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor (
    public providerDataHistoriaClinica: DataHistoriaClinicaService,
    public session: SessionService,
    private DB: DatabaseService,
  ) { }
  
  ngOnInit(): void {
    if (this.session.isSpecialistLevelSession()){
      this.displayedColumns.splice(2, 1);
    } else if (this.session.isPatientLevelSession()){
      this.displayedColumns.splice(1, 1);
    }

    this.providerDataHistoriaClinica.fetchAll().subscribe(
      (response) => {
        this.historiasClinicas = HistoriaClinica.constructorArr(response);

        if (this.session.isPatientLevelSession() && this.session.data){
          this.historiasClinicas = HistoriaClinica.filtrarPorPaciente(this.historiasClinicas, this.session.data.email);
          this.title = 'Historia Clínica de ' + this.session.data.fullName();
          this.especialidades = this.historiasClinicas.map((hc) => { return hc.Turno().especialidad;})
          this.especialidades = [...new Set(this.especialidades)];
        }

        if (this.session.isSpecialistLevelSession() && this.session.data){
          this.historiasClinicas = HistoriaClinica.filtrarPorEspecialista(this.historiasClinicas, this.session.data.email);
        }

        this.setDataSource(this.historiasClinicas);
    });
  }

  descargarHistoriaClinica(){
    if (this.session.isPatientLevelSession() && this.session.data) {
      this.dialog.open(PdfHistoriaClinicaComponent,
        { data : {
          dataSource: this.dataSource,
          paciente: Paciente.filtrarUno(this.DB.pacientes, this.session.data.email),
          especialidad: this.especialidad,
      }});
    }
  }

  filterDataSource(){
    console.log(this.especialidad);
    if (this.especialidad != ''){
      this.setDataSource(this.historiasClinicas.filter((hc) => { return hc.Turno().especialidad == this.especialidad ;}))
    } else {
      this.setDataSource(this.historiasClinicas);
    }
  }

  setDataSource(src: HistoriaClinica[]){
    this.dataSource = new MatTableDataSource<HistoriaClinica>(src);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
}
