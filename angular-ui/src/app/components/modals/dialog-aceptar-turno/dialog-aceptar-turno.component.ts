import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { ColorEstado } from '../../../models/enums/color-estado';

@Component({
  selector: 'app-dialog-aceptar-turno',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule
  ],
  templateUrl: './dialog-aceptar-turno.component.html',
  styleUrl: '../modals.component.css'
})

export class DialogAceptarTurnoComponent {
  readonly dialogRef = inject(MatDialogRef<DialogAceptarTurnoComponent>);
  comentario: string = '';

  volver(): void {
    this.dialogRef.close();
  }

  getBtnColor(): string {
    return ColorEstado.Aceptado;
  }
}
