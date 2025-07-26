import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormDataUsuarioComponent } from '../form-data-usuario/form-data-usuario.component';
import { MatButtonModule } from '@angular/material/button'
import { FormViewerService } from '../../services/form-viewer.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Paciente } from '../../models/paciente';
import { DataUsuariosService } from '../../services/data-usuarios.service';
import { LoginService } from '../../services/login.service';
import { CommonModule } from '@angular/common';
import { NotifierService } from '../../services/notifier.service';
import { RecaptchaModule } from 'ng-recaptcha';
import { LoaderService } from '../../services/loader.service';

@Component({
  selector: 'app-form-alta-paciente',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    FormDataUsuarioComponent,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    RecaptchaModule
  ],
  templateUrl: './form-alta-paciente.component.html',
  styleUrl: '../form-styles.css'
})


export class FormAltaPacienteComponent implements OnInit {
  @Input() submitBtnTxt: string = "Registrarse";
  form: FormGroup = new FormGroup({});
  formPaciente!: FormGroup;
  imagenPerfil!: File;
  imagenPerfilAlt!: File;
  failedRegister: boolean = false;
  registerError: string = "";

  captchaResponse: string | null = null;
  

  constructor (
    public formViewer: FormViewerService,
    private providerDataUsuarios: DataUsuariosService,
    private loginService: LoginService,
    private notifier: NotifierService,
    private loader: LoaderService
  ) { }

  ngOnInit(): void {
    const obraSocial = new FormControl(null, [Validators.required]);
    const imagenPerfilAlt = new FormControl(null, [Validators.required]);
    this.form.addControl('obraSocial', obraSocial);
    this.form.addControl('imagenPerfilAlt', imagenPerfilAlt);
  }
  
  resolved(captchaResponse: any) {
    this.captchaResponse = captchaResponse;
  }

  OnFormSubmitted() {
    this.loader.setLoading(true);
    this.failedRegister = false;
    this.registerError = "";
    let userInfo: any = this.form.get('userInfo');

    if (userInfo){
      let email: string = userInfo.get('email')?.value;
      let pwd: string = userInfo.get('password')?.value;

      this.loginService.Register(email, pwd).then(
        (res) => {
          this.failedRegister = res.error;
          this.registerError = res.msg;
          if (!res.error){
            let paciente: Paciente = new Paciente(
              userInfo.get('nombre')?.value,
              userInfo.get('apellido')?.value,
              parseInt(userInfo.get('edad')?.value),
              parseInt(userInfo.get('dni')?.value),
              userInfo.get('email')?.value,
              userInfo.get('imagenPerfil')?.value,
              "paciente",
              this.form.get('obraSocial')?.value,
              this.form.get('imagenPerfilAlt')?.value,
            );
            
            this.providerDataUsuarios.pushOnePaciente(paciente, this.imagenPerfil, this.imagenPerfilAlt);
            this.form.reset();
            this.notifier.successfullRegisterNotification();
            this.loader.setLoading(false);
          } else {
            this.loader.setLoading(false);
          }
        }
      )
    } else {
      this.loader.setLoading(false);
    }
  }

  controlHasErrors(control: string){
    return this.formViewer.controlHasErrors(this.form, control);
  }

  getControlErrorMessage(control: string){
    return this.formViewer.getControlErrorMessage(this.form, control);
  }

  updateImagenPerfil(file: any){
    this.imagenPerfil = file;
  }

  updateImagenPerfilAlt(e: Event){
    if (e.target != null){
      let target = <HTMLInputElement> e.target;
      if (target != null && target.files != null){
        let file = target.files[0];
        this.imagenPerfilAlt = file;
      }
    }
  }
}
