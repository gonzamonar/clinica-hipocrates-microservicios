import { Injectable } from '@angular/core';
import { Admin } from '../../../shared/models/admin';
import { Especialista } from '../../../shared/models/especialista';
import { Paciente } from '../../../shared/models/paciente';
import { DataUsuariosService } from '../../../services/data-usuarios.service';
import { Observable, Subject } from 'rxjs';
import { TipoUsuario } from '../../../shared/models/enums/tipo-usuario';

@Injectable({
  providedIn: 'root'
})

export class SessionService {
  public data: Admin | Especialista | Paciente | null = null;
  private userData: Subject<Admin | Especialista | Paciente | null> = new Subject();
  private active: boolean = false;

  constructor(
    private userdataProvider: DataUsuariosService,
  ) {
    this.userData.next(null);
    this.userData.subscribe(
      (userdata) => {
        this.data = userdata;
    });
  }

  async updateSession(){
    let userdata: Admin | Especialista | Paciente | null = null;

    if (!this.active){

      await this.userdataProvider.fetchProfile()
        .then(
          (doc) => {
            let nombre = doc.nombre;
            let apellido = doc.apellido;
            let edad = doc.edad;
            let dni = doc.dni;
            let email = doc.email;
            let imagenPerfil = doc.imagenPerfil;
            let nivelUsuario = doc.nivelUsuario;

            switch (nivelUsuario) {
              case TipoUsuario.Paciente:
                userdata = new Paciente(nombre, apellido, edad, dni, email, imagenPerfil, nivelUsuario, doc.obraSocial, doc.imagenPerfilAlt);
                break;
              case TipoUsuario.Especialista:
                userdata = new Especialista(nombre, apellido, edad, dni, email, imagenPerfil, nivelUsuario, doc.especialidad.split(','), doc.habilitado);
                break;
              case TipoUsuario.Admin:
                userdata = new Admin(nombre, apellido, edad, dni, email, imagenPerfil, nivelUsuario);
                break;
            }

            this.userData.next(userdata);
        });
    }
  }

  getCurrentUserData(): Observable<Admin | Especialista | Paciente | null> {
    return this.userData;
  }

  isSessionActive(): boolean {
    return this.active;
  }

  closeSession(): void {
    this.active = false;
    this.userData.next(null);
  }

  isAdminLevelSession(): boolean {
    return this.active && this.data?.nivelUsuario == TipoUsuario.Admin;
  }

  isPatientLevelSession(): boolean {
    return this.active && this.data?.nivelUsuario == TipoUsuario.Paciente;
  }

  isSpecialistLevelSession(): boolean {
    return this.active && this.data?.nivelUsuario == TipoUsuario.Especialista;
  }

  isSessionAuthorized(): boolean {
    return true;
  }

  /*
  isSessionAuthorized(): boolean {
    return this.isSpecialistSessionAuthorized();
  }

  isSpecialistSessionAuthorized(): boolean {
    let authorized = true;

    if (this.isSpecialistLevelSession()){
      if (this.data?.nivelUsuario == this.typeEspecialista){
        let data: Especialista = <Especialista> this.data;
        authorized = data.habilitado;
      }
    }

    return authorized;
  }
    */

  // getEspecialidades(): string[] {
  //   let especialidades: string[] = [];
  //   if (this.isSpecialistLevelSession()){
  //     especialidades = <string[]> (<Especialista>this.data).especialidad;
  //   }
  //   return especialidades;
  // }
}
