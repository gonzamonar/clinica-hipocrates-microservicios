import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { SessionService } from '../../services/session.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSlideToggleModule } from '@angular/material/slide-toggle'
import { MatButtonModule } from '@angular/material/button'
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { NotifierService } from '../../services/notifier.service';
import { DataUsuariosService } from '../../services/data-usuarios.service';
import { Usuario } from '../../models/usuario';
import { MatTooltipModule } from '@angular/material/tooltip';
import { DatabaseService } from '../../services/database.service';
import { LoaderService } from '../../services/loader.service';


@Component({
  selector: 'app-form-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatSlideToggleModule,
    MatButtonModule,
    RouterModule,
    RouterOutlet,
    MatTooltipModule
  ],
  templateUrl: './form-login.component.html',
  styleUrls: ['../form-styles.css', 'form-login.component.css'],
})

export class FormLoginComponent {
  email: string = '';
  password: string = '';
  rememberLogin: boolean = false;
  hide: boolean = true;
  loginError: boolean = false;
  errorMessage: string = '';
  verifyError: boolean = false;

  quickAccessUsers: any[] = [
    {email: 'mr.robot@yopmail.com', pwd: 'sudor00t123'},
    {email: 'rick.sanchez@yopmail.com', pwd: 'rick123456'},
    {email: 'dr.house@yopmail.com', pwd: 'house1234'},
    {email: 'homero.simpson@yopmail.com', pwd: 'novoyamentirtemarge'},
    {email: 'cruz@yopmail.com', pwd: 'penelope'},
    {email: 'king.joffrey@yopmail.com', pwd: 'iamtheking'},
  ];

  constructor(
    public login: LoginService,
    public session: SessionService,
    private router: Router,
    private notifier: NotifierService,
    private DB: DatabaseService,
    public loader: LoaderService
  ) { }

  async Login() {
    if (!this.loader.getLoading()){
      this.loader.setLoading(true);
      this.loginError = false;
      this.verifyError = false;
      this.errorMessage = '';
  
      this.login.signIn(this.email, this.password, this.rememberLogin)
      .then((res) => {
          this.loginError = res;
          if (this.loginError){
            this.errorMessage = "Usuario o contraseña incorrectos.";
          } else {
            if (!this.session.getCurrentUser().emailVerified){
              this.verifyError = true;
              this.login.signOut();
            } else if (!this.session.isSessionAuthorized()) {
              this.loginError = true;
              this.errorMessage = "Un administrador debe habilitar su cuenta para ingresar.";
              this.login.signOut();
            } else {
              this.quickAccess('', '');
              this.notifier.popUpNotification('Bienvenido/a '+ this.session.data?.nombre);
              this.router.navigate(['/']);
            }
          }
        }
      ).then(() => {
        this.loader.setLoading(false);
      });
    }
  }

  Logout() {
    this.login.requireSignOut();
  }

  quickAccess(email: string, pwd: string){
    this.loginError = false;
    this.verifyError = false;
    this.email = email;
    this.password = pwd;
  }

  getImgSrc(email: string){
    let usuario = Usuario.filtrarUno(this.DB.usuarios, email);
    return usuario ? usuario.imagenPerfil : null;
  }

  getTooltipTxt(email: string){
    let txt = '';
    let usuario = Usuario.filtrarUno(this.DB.usuarios, email);
    if (usuario){
      txt = usuario.fullName() + " - " + usuario.nivelUsuario;
    }
    return txt;
  }
}
