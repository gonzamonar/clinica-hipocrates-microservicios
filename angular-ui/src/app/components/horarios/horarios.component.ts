import { Component, OnChanges, SimpleChanges } from '@angular/core';
import { SelectorHorariosComponent } from '../selector-horarios/selector-horarios.component';
import { SessionService } from '../../services/session.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-horarios',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    SelectorHorariosComponent,
    MatFormFieldModule,
    MatSelectModule
  ],
  templateUrl: './horarios.component.html',
  styleUrl: './horarios.component.css'
})
export class HorariosComponent {
  especialista: string = '';

  constructor(
    private session: SessionService
  ){    
    if(this.session.data && this.session.isSpecialistLevelSession()){
      this.especialista = this.session.data.email;
    }
  }
}
