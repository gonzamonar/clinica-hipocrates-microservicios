import { Injectable } from '@angular/core';
import { DataUsuariosService } from './data-usuarios.service';
import { DataTurnosService } from './data-turnos.service';
import { DataHistoriaClinicaService } from './data-historia-clinica.service';
import { Usuario } from '../models/usuario';
import { Especialista } from '../models/especialista';
import { Admin } from '../models/admin';
import { Paciente } from '../models/paciente';
import { Turno } from '../models/turno';
import { HistoriaClinica } from '../models/historia-clinica';
import { Comentario } from '../models/comentario';
import { DataComentariosService } from './data-comentarios.service';
import { ExcelService } from './excel.service';
import { NotifierService } from './notifier.service';
import { Especialidad } from '../models/especialidad';
import { DataEspecialidadesService } from './data-especialidades.service';
import { Horario } from '../models/horario';
import { DataHorariosService } from './data-horarios.service';
import { Encuesta } from '../models/encuesta';
import { DataEncuestasService } from './data-encuestas.service';

@Injectable({
  providedIn: 'root'
})
export class DatabaseService {
  static instance: DatabaseService;

  usuarios: Usuario[] = [];
  pacientes: Paciente[] = [];
  especialistas: Especialista[] = [];
  especialidades: Especialidad[] = [];
  admins: Admin[] = [];
  turnos: Turno[] = [];
  comentarios: Comentario[] = [];
  encuestas: Encuesta[] = [];
  historiasClinicas: HistoriaClinica[] = [];

  constructor(
    private providerDataUsuarios: DataUsuariosService,
    private providerDataTurnos: DataTurnosService,
    private providerDataHistoriaClinica: DataHistoriaClinicaService,
    private providerDataComentarios: DataComentariosService,
    private providerDataEspecialidades: DataEspecialidadesService,
    private providerDataEncuestas: DataEncuestasService,
    private ExcelService: ExcelService,
    private notifier: NotifierService
  ) {
    DatabaseService.instance = this;
    
    this.providerDataUsuarios.fetchAll().subscribe(
      (res) => {
        this.usuarios = Usuario.constructorArr(res);
        this.admins = Admin.constructorArr(res);
        this.pacientes = Paciente.constructorArr(res);
        this.especialistas = Especialista.constructorArr(res);
    });

    this.providerDataTurnos.fetchAll().subscribe(
      (res) => {
        this.turnos = Turno.constructorArr(res);
    });

    this.providerDataEspecialidades.fetchAll().subscribe(
      (res) => {
        this.especialidades = Especialidad.constructorArr(res);
    });

    this.providerDataHistoriaClinica.fetchAll().subscribe(
      (res) => {
        this.historiasClinicas = HistoriaClinica.constructorArr(res);
    });

    this.providerDataComentarios.fetchAll().subscribe(
      (res) => {
        this.comentarios = Comentario.constructorArr(res);
    });

    this.providerDataEncuestas.fetchAll().subscribe(
      (res) => {
        this.encuestas = Encuesta.constructorArr(res);
    });
  }

  descargarExcelUsuarios(){
    const arrays: any[] = [...this.admins, ...this.especialistas, ...this.pacientes];
    const data: any[] = arrays.map((i) => { return Object.assign([], i); });
    data.map(
      (i) => {
        i.especialidad = i.especialidad == undefined ? '' : i.especialidad.join(', ') ;
        i.habilitado = i.habilitado == undefined ? '' : i.habilitado ;
        i.obraSocial = i.obraSocial == undefined ? '' : i.obraSocial ;
        delete i.imagenPerfil;
      });
    this.ExcelService.generateExcel(data, 'usuarios_clinica_hipocrates');
  }

  descargarExcelTurnos(paciente: string) {
    let turnosFiltrados = Turno.filtrarPorPaciente(this.turnos, paciente);
    const data: any[] = [];
    let nombre_paciente: string = Usuario.filtrarUno(this.usuarios, paciente).fullName();

    if (turnosFiltrados.length > 0){
      turnosFiltrados.map((t: Turno) => {
        let t2: any = Object.assign([], t);
        t2.especialista = Usuario.filtrarUno(this.usuarios, t.especialista).fullName();
        t2.paciente = Usuario.filtrarUno(this.usuarios, t.paciente).fullName();
        delete t2.nro_comentario;
        delete t2.nro_review;
        delete t2.nro_encuesta;
        delete t2.calificacion;
        delete t2.nro_historia_clinica;
        
        data.push(t2);
      })
  
      this.ExcelService.generateExcel(data, 'turnos_clinica_hipocrates (' + nombre_paciente +')');
    } else {
      this.notifier.popUpNotification("Este paciente aún no solicitó turnos");
    }
  }
}
