import { Component } from '@angular/core';
import { SessionService } from '../../services/session.service';
import { CommonModule, registerLocaleData } from '@angular/common';
import { Especialista } from '../../models/especialista';
import { Paciente } from '../../models/paciente';
import LocaleEsAr from '@angular/common/locales/es-AR'
registerLocaleData(LocaleEsAr, 'es-AR');

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent {

  constructor(
    public session: SessionService
  ){ }
  

  getImagenPerfilAlt(): string {
    let value = '';
    if (this.session.data != null && this.session.isPatientLevelSession()) {
      let data = <Paciente> this.session.data;
      value = data.imagenPerfilAlt;
    }
    return value;
  }

  getObraSocial(): string {
    let value = '';
    if (this.session.data != null && this.session.isPatientLevelSession()) {
      let data = <Paciente> this.session.data;
      value = data.obraSocial;
    }
    return value;
  }
  
  getEspecialidad(): string {
    let value = '';
    if (this.session.data != null && this.session.isSpecialistLevelSession()) {
      let data = <Especialista> this.session.data;
      value = data.especialidad.join(",");
    }
    return value;
  }

  getHabilitado(): string {
    let value = '';
    if (this.session.data != null && this.session.isSpecialistLevelSession()) {
      let data = <Especialista> this.session.data;
      value = data.habilitado ? "habilitado" : "deshabilitado";
    }
    return value;
  }
}
