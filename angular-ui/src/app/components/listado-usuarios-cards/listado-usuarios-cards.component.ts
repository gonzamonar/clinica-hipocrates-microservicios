import { Component } from '@angular/core';
import { DatabaseService } from '../../services/database.service';
import { CommonModule } from '@angular/common';
import { CardUsuarioComponent } from '../card-usuario/card-usuario.component';

@Component({
  selector: 'app-listado-usuarios-cards',
  standalone: true,
  imports: [
    CommonModule,
    CardUsuarioComponent
  ],
  templateUrl: './listado-usuarios-cards.component.html',
  styleUrl: './listado-usuarios-cards.component.css'
})
export class ListadoUsuariosCardsComponent {

  constructor(
    public DB: DatabaseService,
  ){ }

}
