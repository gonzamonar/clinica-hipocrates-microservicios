import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

export interface CommentDialogData {
  usuario: string;
  tipo: string;
  foto: string;
  motivo: string;
  comentario: string;
}

@Component({
  selector: 'app-dialog-comentario-turno',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormField,
    MatInputModule
  ],
  templateUrl: './dialog-comentario-turno.component.html',
  styleUrls: ['../modals.component.css', './dialog-comentario-turno.component.css']
})

export class DialogComentarioTurnoComponent {
  readonly dialogRef = inject(MatDialogRef<DialogComentarioTurnoComponent>);
  readonly data = inject<CommentDialogData>(MAT_DIALOG_DATA);

  volver(): void {
    this.dialogRef.close();
  }
}
