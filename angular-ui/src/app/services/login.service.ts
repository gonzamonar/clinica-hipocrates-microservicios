import { Injectable } from '@angular/core';
import { browserLocalPersistence, inMemoryPersistence, setPersistence } from 'firebase/auth';
import Swal from 'sweetalert2'; //https://sweetalert2.github.io
import { signOut } from 'firebase/auth';
import { SessionService } from './session.service';
import { Auth, createUserWithEmailAndPassword, sendEmailVerification, signInWithEmailAndPassword } from '@angular/fire/auth';
import { NotifierService } from './notifier.service';
import { Router } from '@angular/router';
import { LoggerService } from './logger.service';

@Injectable({
  providedIn: 'root'
})

export class LoginService {

  constructor(
    public auth: Auth,
    public session: SessionService,
    private router: Router,
    private notifier: NotifierService,
    private logger: LoggerService
  ) { }

  async Register(email: string, pwd: string) {
    let registerError: any = { error: true, msg: "No pudo realizarse el registro." };
    let currentUser = this.auth.currentUser;
    let restoreSession: boolean = false;
    if (this.session.isAdminLevelSession()){
      restoreSession = true;
    }

    return createUserWithEmailAndPassword(this.auth, email, pwd)
    .then(
      (response) => {
        if(response.user.email !== null) {
          registerError = { error: false, msg: "Registro exitoso." };
          sendEmailVerification(response.user).then(() => {
            signOut(this.auth).then(() => {
              if (restoreSession){
                this.auth.updateCurrentUser(currentUser);
              }
            }
            );
          });
        }
      }).catch((e) => {
        if (e.code == "auth/email-already-in-use" ||
          e.code == "auth/email-already-exists") {
            registerError = { error: true, msg: "Ya existe una cuenta con ese Email." };
        } else if (e.code.includes("email")) {
          registerError = { error: true, msg: "El Email ingresado es inválido." };
        } else if (e.code.includes("password")){
          registerError = { error: true, msg: "La Contraseña ingresada es inválida." };
        } else {
          registerError = { error: true, msg: "Email o contrañeña inválidos." };
        }
      }).then(() => {
        return registerError;
      });
  }

  async signIn(email: string, password: string, remember: boolean = false): Promise<boolean> {
    let persistence = remember ? browserLocalPersistence : inMemoryPersistence ;
    let loginError: boolean;

    return setPersistence(this.auth, persistence)
    .then(() =>
      signInWithEmailAndPassword(this.auth, email, password)
      .then(
        async (response) => {
          if (response.user != null){
            if (response.user.email != null){
              this.logger.createLog(response.user.email);
            }
            await this.session.updateSession(response.user);
            loginError = false;
          }
        }
      ).catch((e) => {
        loginError = true;
      })
    )
    .catch((error) => {
      console.error('Error setting session persistence:', error);
      loginError = true;
    })
    .then(() => {
      return loginError;
    });
  }

  requireSignOut(){
    Swal.fire({
      title: "¿Deseas cerrar sesión?",
      text: "",
      icon: "question",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#42b54d",
      confirmButtonText: "Sí, salir",
      cancelButtonText: "Cancelar"
    }).then((result) => {
      if (result.isConfirmed) {
        this.signOut();
        this.router.navigateByUrl("/");
        this.notifier.popUpNotification("Sesión fue cerrada exitosamente.");
      }
    });
  }

  signOut(){
    signOut(this.auth);
  }

  async resendEmailVerification(email: string, password: string){
    return signInWithEmailAndPassword(this.auth, email, password)
    .then(
      async (response) => {
        if (!response.user.emailVerified){
          return sendEmailVerification(response.user)
          .then(() => {
            signOut(this.auth);
          })
          .then(() => {
            return { resend: true, message: "Envío exitoso." };
          });
        } else {
          return signOut(this.auth)
          .then(() => {
            return { resend: false, message: "El email ya se encuentra verificado." };
          });
        }
      }
    ).catch((e) => {
      return { resend: false, message: "Verifique los datos ingresados e inténtelo nuevamente." };
    })
  }
}
