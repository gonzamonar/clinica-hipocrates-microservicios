import { Injectable } from '@angular/core';
import { Admin } from '../models/admin';
import { Especialista } from '../models/especialista';
import { Paciente } from '../models/paciente';
import { DataUsuariosService } from './data-usuarios.service';
import { Auth } from '@angular/fire/auth';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class SessionService {
  public data: Admin | Especialista | Paciente | null = null;
  private user: any = null;
  private userData: Subject<Admin | Especialista | Paciente | null> = new Subject();
  private typePaciente: string = 'paciente';
  private typeEspecialista: string = 'especialista';
  private typeAdmin: string = 'admin';


  constructor(
    private userdataProvider: DataUsuariosService,
    public auth: Auth
  ) {
    this.userData.next(null);
    this.userData.subscribe(
      (userdata) => {
        this.data = userdata;
    });
  }

  async updateSession(user: any){
    let userdata: Admin | Especialista | Paciente | null = null;

    if (user){
      this.user = user;
      
      await this.userdataProvider.fetchOne(user.email)
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
            case this.typePaciente:
              userdata = new Paciente(nombre, apellido, edad, dni, email, imagenPerfil, nivelUsuario, doc.obraSocial, doc.imagenPerfilAlt);
              break;
            case this.typeEspecialista:
              userdata = new Especialista(nombre, apellido, edad, dni, email, imagenPerfil, nivelUsuario, doc.especialidad.split(','), doc.habilitado);
              break;
            case this.typeAdmin:
              userdata = new Admin(nombre, apellido, edad, dni, email, imagenPerfil, nivelUsuario);
              break;
          }

          this.userData.next(userdata);
      });
    }
  }

  getCurrentUser(): any {
    return this.user;
  }

  getCurrentUserData(): Observable<Admin | Especialista | Paciente | null> {
    return this.userData;
  }

  isSessionActive(): boolean {
    return this.user != null;
  }

  closeSession(): void {
    this.user = null;
    this.userData.next(null);
  }

  isSessionAuthorized(): boolean {
    return this.isSpecialistSessionAuthorized();
  }

  isAdminLevelSession(): boolean {
    return this.user != null && this.data?.nivelUsuario == this.typeAdmin;
  }

  isPatientLevelSession(): boolean {
    return this.user != null && this.data?.nivelUsuario == this.typePaciente;
  }

  isSpecialistLevelSession(): boolean {
    return this.user != null && this.data?.nivelUsuario == this.typeEspecialista;
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

  // getEspecialidades(): string[] {
  //   let especialidades: string[] = [];
  //   if (this.isSpecialistLevelSession()){
  //     especialidades = <string[]> (<Especialista>this.data).especialidad;
  //   }
  //   return especialidades;
  // }
}
