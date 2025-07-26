import { AfterViewChecked, Component } from '@angular/core';
import { DatabaseService } from '../../services/database.service';
import { SessionService } from '../../services/session.service';
import { CommonModule } from '@angular/common';
import { Paciente } from '../../models/paciente';
import { Especialista } from '../../models/especialista';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Especialidad } from '../../models/especialidad';
import { DataHorariosService } from '../../services/data-horarios.service';
import { Horario } from '../../models/horario';
import { ToDatePipe } from '../../pipes/to-date.pipe';
import { ToTimePipe } from '../../pipes/to-time.pipe';
import { MatBadgeModule } from '@angular/material/badge';
import { MatButtonModule } from '@angular/material/button';
import { Turno } from '../../models/turno';
import { DataTurnosService } from '../../services/data-turnos.service';
import { NotifierService } from '../../services/notifier.service';
import { LoaderService } from '../../services/loader.service';

@Component({
  selector: 'app-form-solicitud-turno',
  standalone: true,
  imports: [
    CommonModule,
    MatTooltipModule,
    ToDatePipe,
    ToTimePipe,
    MatBadgeModule,
    MatButtonModule,
  ],
  templateUrl: './form-solicitud-turno.component.html',
  styleUrls: ['../form-styles.css', './form-solicitud-turno.component.css']
})

export class FormSolicitudTurnoComponent {
  assetsDir: string = '/assets/img/especialidades/';
  step: number = 1;
  opcionesHorarios!: Horario[];
  opcionesFechas!: string[];
  opcionesHoras!: string[];

  paciente: Paciente | null = null;
  especialista: Especialista | null = null;
  especialidad: string = '';
  fecha: string = '';
  hora: string = '';

  constructor(
    public DB: DatabaseService,
    public session: SessionService,
    private providerDataHorarios: DataHorariosService,
    private providerDataTurnos: DataTurnosService,
    private notifier: NotifierService,
    private loader: LoaderService,
  ) {

  }

  getSrc(e: string){
    return this.assetsDir + Especialidad.filtrarUno(this.DB.especialidades, e).urlImagen;
  }

  
  seleccionarPaciente(paciente: Paciente){
    if (this.paciente != paciente){
      this.paciente = paciente;
      this.especialista = null;
      this.step = 1;
    }
  }
  
  seleccionarEspecialista(especialista: Especialista){
    if (this.especialista != especialista){
      this.especialista = especialista;
      this.actualizarFechas();
      this.especialidad = '';
      this.fecha = '';
      this.hora = '';
      this.step = 2;
    }
  }
  
  seleccionarEspecialidad(especialidad: string){
    if (this.especialidad != especialidad){
      this.especialidad = especialidad;
      this.fecha = '';
      this.hora = '';
      this.step = 3;
    }
  }
  
  seleccionarFecha(fecha: string){
    if (this.fecha != fecha){
      this.fecha = fecha;
      this.actualizarHoras();
      this.hora = '';
      this.step = 4;
    }
  }
  
  seleccionarHora(hora: string){
    if (this.hora != hora){
      this.hora = hora;
      this.step = 5;
    }

    if (this.session.isPatientLevelSession() && this.session.data != null && this.session.data != undefined){
      this.paciente = <Paciente> this.session.data;
    }
  }

  actualizarFechas(){
    if (this.especialista){
      let especialista = this.especialista.email;
      this.providerDataHorarios.fetchOne(especialista)
      .then(
        (data) => {
          this.opcionesHorarios = this.providerDataHorarios.crearArrayHorarios(
            data.horarios.split(","),
            this.providerDataHorarios.crearArrFechas(15)
          )
          this.opcionesHorarios = this.providerDataHorarios.quitarHorariosUsados(this.opcionesHorarios, especialista);
  
          this.opcionesFechas = this.opcionesHorarios.map((h) => { return h.dia; });
          this.opcionesFechas = [...new Set(this.opcionesFechas)];
      });
    }
  }

  actualizarHoras(){
    this.opcionesHoras = this.opcionesHorarios.filter((i) => { return i.dia == this.fecha }).map((i) => { return i.hora; });
  }

  getRecuentoFecha(dia: string){
    return Horario.contarDias(this.opcionesHorarios, dia);
  }

  confirmarTurno(){
      this.loader.setLoading(true);
  
      if (this.paciente && this.especialista && this.especialidad != '' && this.fecha != '' && this.hora != '') {
        let turno = new Turno(
          0,
          this.especialista.email,
          this.especialidad,
          this.paciente.email,
          this.fecha,
          this.hora
        );
        
        this.providerDataTurnos.pushOne(turno)
        .then(
          (res) => {
            if (res){
              this.notifier.popUpNotification("Turno confirmado exitosamente.");
              this.step = 1;
              this.especialista = null;
            } else {
              this.notifier.popUpNotification("No se pudo confirmar el turno, inténtelo nuevamente.");
            }
            this.loader.setLoading(false);
          })
        } else {
        this.notifier.popUpNotification("Ha ocurrido un error en los campos ingresados.");
        this.loader.setLoading(false);
    }
  }
}
