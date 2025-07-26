import { Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { MatTooltipModule } from '@angular/material/tooltip';
import LocaleEsAr from '@angular/common/locales/es-AR'
import { CommonModule, registerLocaleData } from '@angular/common';
import { ToTitleCasePipe } from '../../pipes/to-title-case.pipe';
import { Especialista } from '../../models/especialista';
import { Paciente } from '../../models/paciente';
import { DatabaseService } from '../../services/database.service';
import { RenderBooleanFieldPipe } from '../../pipes/render-boolean-field.pipe';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { LoaderService } from '../../services/loader.service';
import { DataUsuariosService } from '../../services/data-usuarios.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFileExcel, faImage, IconDefinition } from '@fortawesome/free-solid-svg-icons';
registerLocaleData(LocaleEsAr, 'es-AR');

@Component({
  selector: 'app-card-usuario',
  standalone: true,
  imports: [
    CommonModule,
    MatTooltipModule,
    ToTitleCasePipe,
    RenderBooleanFieldPipe,
    MatButtonModule,
    MatIconModule,
    FontAwesomeModule
  ],
  templateUrl: './card-usuario.component.html',
  styleUrl: './card-usuario.component.css'
})

export class CardUsuarioComponent implements OnChanges {
  @Input() usuario!: Usuario | Especialista | Paciente;
  especialista: Especialista | null = null;
  paciente: Paciente | null = null;
  iconXls: IconDefinition = faFileExcel;
  iconImg: IconDefinition = faImage;

  constructor(
    private providerDataUsuarios: DataUsuariosService,
    public DB: DatabaseService,
    public loader: LoaderService,
  ) { }

  @ViewChild('profilePic') profilePic!: ElementRef;

  ngOnChanges(): void {
    if (this.usuario && this.usuario.esPaciente()){
      this.paciente = Paciente.filtrarUno(this.DB.pacientes, this.usuario.email);
    }
    if (this.usuario && this.usuario.esEspecialista()){
      this.especialista = Especialista.filtrarUno(this.DB.especialistas, this.usuario.email);
    }
  }
  
  habilitar(email: string, habilitacionActual: boolean | null){
    this.loader.setLoading(true);
    if (habilitacionActual != null){
      this.providerDataUsuarios.updateHabilitacionEspecialista(email, habilitacionActual)
      .then(() => {
        this.loader.setLoading(false);
      });
    } else {
      this.loader.setLoading(false);
    }
  }

  switchImgPerfil(){
    if (this.paciente) {
      let el = <HTMLImageElement> this.profilePic.nativeElement;
      el.src = el.src == this.paciente.imagenPerfil ? this.paciente.imagenPerfilAlt : this.paciente.imagenPerfil ;
    }
  }

  renderEspecialidades(){
    let especialidades: string = '';
    if (this.usuario.esEspecialista() && this.especialista && this.especialista.especialidad){
      especialidades = this.especialista.especialidad.join(', ');
    }
    return especialidades;
  }
}
