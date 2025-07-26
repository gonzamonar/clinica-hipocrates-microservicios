import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ColorEstado } from '../../../models/enums/color-estado';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-dialog-rechazar-turno',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormField,
    MatInputModule,
    MatTooltipModule,
  ],
  templateUrl: './dialog-rechazar-turno.component.html',
  styleUrl: '../modals.component.css'
})

export class DialogRechazarTurnoComponent {
  readonly dialogRef = inject(MatDialogRef<DialogRechazarTurnoComponent>);
  comentario: string = '';

  volver(): void {
    this.dialogRef.close();
  }

  getBtnColor(): string {
    return ColorEstado.Rechazado;
  }
}
