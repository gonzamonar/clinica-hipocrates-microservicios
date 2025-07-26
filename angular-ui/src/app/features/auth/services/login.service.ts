import { Injectable } from '@angular/core';
import Swal from 'sweetalert2'; //https://sweetalert2.github.io
import { SessionService } from './session.service';
import { NotifierService } from '../../../core/services/notifier.service';
import { Router } from '@angular/router';
import { LoaderService } from '../../../core/services/loader.service';
import { AuthService } from './auth.service';


@Injectable({
  providedIn: 'root'
})

export class LoginService {

  constructor(
    public authService: AuthService,
    public session: SessionService,
    private router: Router,
    private notifier: NotifierService,
    private loader: LoaderService
  ) { }

  async login(email: string, password: string): Promise<boolean> {
    this.loader.setLoading(true);
    return this.authService.signIn(email, password)
    .then((response) => {
        this.loader.setLoading(false);
        if (!response.error){
          this.session.updateSession();
          this.router.navigateByUrl("/inicio");
        }
        return response.error;
    });
  }





  async Register(email: string, pwd: string) {
    let registerError: any = { error: true, msg: "No pudo realizarse el registro." };
    /*
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
        */
     return registerError;
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
        this.session.closeSession();
        this.router.navigateByUrl("/");
        this.notifier.popUpNotification("Sesión fue cerrada exitosamente.");
      }
    });
  }

  signOut(){
    //signOut(this.auth);
  }

  async resendEmailVerification(email: string, password: string){
    /*
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
    */
  }
}
