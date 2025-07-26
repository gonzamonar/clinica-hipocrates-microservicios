import { CommonModule } from '@angular/common';
import { AfterContentChecked, Component, Input, inject } from '@angular/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { SessionService } from '../../services/session.service';
import { IconDefinition, faBan, faCircleCheck, faFlagCheckered, faHourglass, faStar, faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { RenderNullStringFieldPipe } from '../../pipes/render-null-string-field.pipe';
import { MatIconModule } from '@angular/material/icon';
import { Turno } from '../../models/turno';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { DialogCancelComponent } from '../modals/dialog-cancel/dialog-cancel.component';
import { take } from 'rxjs';
import { NotifierService } from '../../services/notifier.service';
import { Estado } from '../../models/enums/estado';
import { ColorEstado } from '../../models/enums/color-estado';
import { DataTurnosService } from '../../services/data-turnos.service';
import { DialogComentarioTurnoComponent } from '../modals/dialog-comentario-turno/dialog-comentario-turno.component';
import { Usuario } from '../../models/usuario';
import { DialogAceptarTurnoComponent } from '../modals/dialog-aceptar-turno/dialog-aceptar-turno.component';
import { DialogRechazarTurnoComponent } from '../modals/dialog-rechazar-turno/dialog-rechazar-turno.component';
import { DialogFinalizarTurnoComponent } from '../modals/dialog-finalizar-turno/dialog-finalizar-turno.component';
import { DialogCalificarTurnoComponent } from '../modals/dialog-calificar-turno/dialog-calificar-turno.component';
import { DialogCargarReviewComponent } from '../modals/dialog-cargar-review/dialog-cargar-review.component';
import { DialogCargarHistoriaClinicaComponent } from '../modals/dialog-cargar-historia-clinica/dialog-cargar-historia-clinica.component';
import { DataHistoriaClinicaService } from '../../services/data-historia-clinica.service';
import { HistoriaClinica } from '../../models/historia-clinica';
import { DatoDinamico } from '../../models/dato-dinamico';
import { MatChipsModule } from '@angular/material/chips';
import { ToTitleCasePipe } from '../../pipes/to-title-case.pipe';
import { DatabaseService } from '../../services/database.service';
import { Comentario } from '../../models/comentario';
import { TipoComentario } from '../../models/enums/tipo-comentario';
import { DataComentariosService } from '../../services/data-comentarios.service';
import { ToDatePipe } from '../../pipes/to-date.pipe';
import { ToTimePipe } from '../../pipes/to-time.pipe';
import { HighlightDirective } from '../../directives/highlight.directive';
import { DialogCargarEncuestaComponent } from '../modals/dialog-cargar-encuesta/dialog-cargar-encuesta.component';
import { Encuesta } from '../../models/encuesta';
import { DataEncuestasService } from '../../services/data-encuestas.service';
import { DialogMostrarEncuestaComponent } from '../modals/dialog-mostrar-encuesta/dialog-mostrar-encuesta.component';

@Component({
  selector: 'app-card-turno',
  standalone: true,
  imports: [
    CommonModule,
    MatTooltipModule,
    FontAwesomeModule,
    RenderNullStringFieldPipe,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    MatChipsModule,
    ToTitleCasePipe,
    ToDatePipe,
    ToTimePipe,
    HighlightDirective
  ],
  templateUrl: './card-turno.component.html',
  styleUrl: './card-turno.component.css'
})

export class CardTurnoComponent implements AfterContentChecked {
  @Input() turno!: Turno;
  @Input() searchStr: string = '';
  historiaClinica: HistoriaClinica | null = null;
  
  readonly dialog = inject(MatDialog);
  iconStar: IconDefinition = faStar;

  constructor(
    private session: SessionService,
    private notifier: NotifierService,
    private providerDataTurnos: DataTurnosService,
    private providerDataHistoriaClinica: DataHistoriaClinicaService,
    private providerDataComentarios: DataComentariosService,
    private providerDataEncuestas: DataEncuestasService,
    private DB: DatabaseService
  ){ }

  ngAfterContentChecked(): void {
    if (this.turno != null && this.turno.nro_historia_clinica != null){
      this.historiaClinica = this.turno.HistoriaClinica();
    }
  }
  
  displayTooltipCalificacion(calificacion: number | null){
    return calificacion == null ? "Calificación pendiente" : "Calificación del paciente" ;
  }

  displayTooltipComentario(comentario: number | null){
    return comentario == null ? "Todavía no se han agregado comentarios." : "Ver Comentarios" ;
  }

  displayTooltipReview(review: number | null){
    return review == null ? "Todavía no se ha agregado una reseña." : "Ver Reseña del Especialista" ;
  }

  displayTooltipEncuesta(encuesta: number | null){
    return encuesta == null ? "La encuesta aún no ha sido realizada." : "Ver Encuesta realizada por el Paciente" ;
  }

  getStatusIcon(status: string){
    let icon: IconDefinition = faHourglass; //pendiente
    switch (status){
      case Estado.Cancelado:
        icon = faXmark;
        break;
      case Estado.Rechazado:
        icon = faBan;
        break;
      case Estado.Aceptado:
        icon = faCircleCheck;
        break;
      case Estado.Realizado:
        icon = faFlagCheckered;
        break;
    }
    return icon;
  }

  getStatusBgColor(status: string){
    let color: string = ColorEstado.Pendiente;
    switch (status){
      case Estado.Pendiente:
        color = ColorEstado.Pendiente;
        break;
      case Estado.Cancelado:
        color = ColorEstado.Cancelado;
        break;
      case Estado.Rechazado:
        color = ColorEstado.Rechazado;
        break;
      case Estado.Aceptado:
        color = ColorEstado.Aceptado;
        break;
      case Estado.Realizado:
        color = ColorEstado.Realizado;
        break;
    }
    return color;
  }


  displayCancelBtn(turno: Turno): boolean {
    return (this.session.isPatientLevelSession() && turno.estado == Estado.Pendiente)
        || (this.session.isSpecialistLevelSession() && turno.estado == Estado.Pendiente)
        || (this.session.isAdminLevelSession() && turno.estado == Estado.Pendiente);
  }

  displayRejectBtn(turno: Turno): boolean {
    return (this.session.isSpecialistLevelSession() && turno.estado == Estado.Pendiente);
  }

  displayAcceptBtn(turno: Turno): boolean {
    return (this.session.isSpecialistLevelSession() && turno.estado == Estado.Pendiente);
  }

  displayFinishBtn(turno: Turno): boolean {
    return (this.session.isSpecialistLevelSession() && turno.estado == Estado.Aceptado);
  }

  displayCompleteQuizBtn(turno: Turno): boolean {
    return (this.session.isPatientLevelSession() && turno.nro_review != null && turno.nro_encuesta == null && turno.estado == Estado.Realizado);
  }

  displayCalificationBtn(turno: Turno): boolean {
    return (this.session.isPatientLevelSession() && turno.estado == Estado.Realizado && turno.calificacion == null);
  }

  displayCompleteReviewBtn(turno: Turno): boolean {
    return (this.session.isSpecialistLevelSession() && turno.nro_review == null && turno.estado == Estado.Realizado);
  }

  displayUpdateClinicHistoryBtn(turno: Turno): boolean {
    return (this.session.isSpecialistLevelSession() && turno.nro_historia_clinica == null && turno.estado == Estado.Realizado);
  }

  displayShowReviewBtn(turno: Turno): boolean {
    return !(turno.estado == Estado.Cancelado || turno.estado == Estado.Rechazado);
  }

  displayShowEncuestaBtn(turno: Turno): boolean {
    return !(turno.estado == Estado.Cancelado || turno.estado == Estado.Rechazado);
  }

  showComment(nro_comentario: number | null){
    if (nro_comentario != null && nro_comentario != undefined){
      let comentario = Comentario.filtrarUno(this.DB.comentarios, nro_comentario);
      let comentador = Usuario.filtrarUno(this.DB.usuarios, comentario.comentador);
      this.dialog.open(DialogComentarioTurnoComponent,
        { data : {
          usuario: comentador.fullName(),
          tipo: comentador.nivelUsuario,
          foto: comentador.imagenPerfil,
          motivo: comentario.motivo,
          comentario: comentario.comentario,
        }}
      );
    }
  }

  showReview(nro_review: number | null){
    if (nro_review != null && nro_review != undefined){
      let review = Comentario.filtrarUno(this.DB.comentarios, nro_review);
      let comentador = review.Comentador();
      this.dialog.open(DialogComentarioTurnoComponent,
        { data : {
          usuario: comentador.fullName(),
          tipo: comentador.nivelUsuario,
          foto: comentador.imagenPerfil,
          motivo: review.motivo,
          comentario: review.comentario,
        }}
      );
    }
  }

  showEncuesta(turno: Turno){
    if (turno.nro_encuesta != null){
      let encuesta = turno.Encuesta();
      this.dialog.open(DialogMostrarEncuestaComponent,
        { data : encuesta }
      );
    }
  }

  actionAcceptBtn(turno: Turno) {
    const dialogRef = this.dialog.open(DialogAceptarTurnoComponent);

    dialogRef.afterClosed()
    .pipe(take(1))
    .subscribe((res) => {
      if (res === true) {
        this.providerDataTurnos.aceptarTurno(turno);
      }
    });
  }

  actionCancelBtn(turno: Turno) {
    const dialogRef = this.dialog.open(DialogCancelComponent);

    dialogRef.afterClosed()
    .pipe(take(1))
    .subscribe((response) => {
      if(response != undefined){
        if (response != '' && this.session.data){
          let comentario = new Comentario(
            0,
            turno.nro_turno,
            TipoComentario.Comentario,
            this.session.data.email,
            "Motivo de cancelación",
            response
          )
          this.providerDataTurnos.cancelarTurno(turno);
          this.providerDataComentarios.agregarComentario(comentario);
        } else {
          this.notifier.alert("El comentario es obligatorio", "Debe agregar un comentario a la cancelación", "error");
        }
      }
    });
  }

  actionRejectBtn(turno: Turno) {
    const dialogRef = this.dialog.open(DialogRechazarTurnoComponent);

    dialogRef.afterClosed()
    .pipe(take(1))
    .subscribe((response) => {
      if(response != undefined){
        if (response != '' && this.session.data){
          let comentario = new Comentario(
            0,
            turno.nro_turno,
            TipoComentario.Comentario,
            this.session.data.email,
            "Motivo de rechazo",
            response
          )
          this.providerDataTurnos.rechazarTurno(turno);
          this.providerDataComentarios.agregarComentario(comentario);
        } else {
          this.notifier.alert("El comentario es obligatorio", "Debe agregar un comentario al rechazo del turno", "error");
        }
      }
    });
  }

  actionFinishBtn(turno: Turno) {
    const dialogRef = this.dialog.open(DialogFinalizarTurnoComponent);

    dialogRef.afterClosed()
    .pipe(take(1))
    .subscribe((res) => {
      if (res === true) {
        this.providerDataTurnos.finalizarTurno(turno);
      }
    });
  }

  actionCalificationBtn(turno: Turno) {
    const dialogRef = this.dialog.open(DialogCalificarTurnoComponent);

    dialogRef.afterClosed()
    .pipe(take(1))
    .subscribe((result) => {
      if (result.comentario != '' && result.rating != 0 && this.session.data) {
        let comentario = new Comentario(
          0,
          turno.nro_turno,
          TipoComentario.Comentario,
          this.session.data.email,
          "Comentario de calificación",
          result.comentario
        )
        this.providerDataTurnos.calificarTurno(turno, parseInt(result.rating));
        this.providerDataComentarios.agregarComentario(comentario);
      }
    });
  }

  actionCompleteReviewBtn(turno: Turno) {
    const dialogRef = this.dialog.open(DialogCargarReviewComponent);

    dialogRef.afterClosed()
    .pipe(take(1))
    .subscribe((response) => {
      if(response != undefined && response != '' && this.session.data){
        let review = new Comentario(
          0,
          turno.nro_turno,
          TipoComentario.Review,
          this.session.data.email,
          "Reseña del turno",
          response
        )
        this.providerDataComentarios.agregarComentario(review);
      }
    });
  }

  actionCompleteQuizBtn(turno: Turno) {
    const dialogRef = this.dialog.open(DialogCargarEncuestaComponent);

    dialogRef.afterClosed()
    .pipe(take(1))
    .subscribe((response) => {
      if (response){

        let encuesta = new Encuesta(
          0,
          turno.nro_turno,
          response.rating_pagina,
          response.comentario_pagina,
          response.rating_especialista,
          response.comentario_especialista,
          response.rating_turno,
          response.comentario_turno,
        )
        
        if (encuesta){
          this.providerDataEncuestas.pushOne(encuesta);
        }
      }
    });
  }

  actionUpdateClinicHistoryBtn(turno: Turno) {
    const dialogRef = this.dialog.open(DialogCargarHistoriaClinicaComponent);

    dialogRef.afterClosed()
    .pipe(take(1))
    .subscribe((response) => {
      if (response){

        let historia = new HistoriaClinica(
          0,
          turno.nro_turno,
          turno.paciente,
          turno.especialista,
          parseInt(response.altura),
          parseInt(response.peso),
          parseInt(response.temperatura),
          parseInt(response.presion),
          this.buildDato(response.dato1clave, response.dato1valor),
          this.buildDato(response.dato2clave, response.dato2valor),
          this.buildDato(response.dato3clave, response.dato3valor),
        );
        
        if (historia){
          this.providerDataHistoriaClinica.pushOne(historia);
        }
      }
    });
  }

  buildDato(clave: string, valor: string){
    return clave != '' && valor != '' ? new DatoDinamico(clave, valor) : null ;
  }

}
