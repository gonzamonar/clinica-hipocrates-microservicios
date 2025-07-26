import { Component } from '@angular/core';
import { FormgroupAltaUsuariosComponent } from '../../components/formgroup-alta-usuarios/formgroup-alta-usuarios.component';
import { ListadoUsuariosComponent } from '../../components/listado-usuarios/listado-usuarios.component';
import { MatTabsModule } from '@angular/material/tabs';
import { SessionService } from '../../services/session.service';
import { CommonModule } from '@angular/common';
import { ListadoHistoriaClinicaComponent } from '../../components/listado-historia-clinica/listado-historia-clinica.component';
import { ListadoUsuariosCardsComponent } from '../../components/listado-usuarios-cards/listado-usuarios-cards.component';
import { MatButtonModule } from '@angular/material/button';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFileExcel, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { DatabaseService } from '../../services/database.service';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [
    ListadoHistoriaClinicaComponent,
    FormgroupAltaUsuariosComponent,
    ListadoUsuariosComponent,
    ListadoUsuariosCardsComponent,
    CommonModule,
    MatTabsModule,
    MatButtonModule,
    FontAwesomeModule
  ],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css'
})

export class UsuariosComponent {
  cardsView: boolean = true;
  iconXls: IconDefinition = faFileExcel;

  constructor(
    public session: SessionService,
    public DB: DatabaseService
  ) { }
}
