import { Component } from '@angular/core';
import { FormAltaEspecialistaComponent } from '../form-alta-especialista/form-alta-especialista.component';
import { FormAltaPacienteComponent } from '../form-alta-paciente/form-alta-paciente.component';
import { MatTabsModule } from '@angular/material/tabs';
import { FormAltaAdminComponent } from '../form-alta-admin/form-alta-admin.component';

@Component({
  selector: 'app-formgroup-alta-usuarios',
  standalone: true,
  imports: [
    MatTabsModule,
    FormAltaPacienteComponent,
    FormAltaEspecialistaComponent,
    FormAltaAdminComponent
  ],
  templateUrl: './formgroup-alta-usuarios.component.html',
  styleUrl: '../form-styles.css'
})
export class FormgroupAltaUsuariosComponent {

}
