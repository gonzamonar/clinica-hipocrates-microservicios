import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ColorEstado } from '../../../models/enums/color-estado';
import { MatSliderModule } from '@angular/material/slider';
import { RatingModule } from 'primeng/rating';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-dialog-calificar-turno',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormField,
    MatInputModule,
    MatSliderModule,
    MatTooltipModule,
    RatingModule,
  ],
  templateUrl: './dialog-calificar-turno.component.html',
  styleUrl: '../modals.component.css'
})

export class DialogCalificarTurnoComponent {
  readonly dialogRef = inject(MatDialogRef<DialogCalificarTurnoComponent>);
  comentario: string = '';
  rating: number = 0;

  volver(): void {
    this.dialogRef.close();
  }

  getBtnColor(): string {
    return ColorEstado.Aceptado;
  }

  isDisabled(): boolean {
    return this.comentario == '' || this.rating == 0;
  }

  getTooltipTxt(): string {
    return this.isDisabled() ? 'Es obligatorio agregar un comentario y una calificación' : '' ;
  }
}
