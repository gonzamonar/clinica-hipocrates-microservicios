import { Component, inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { Encuesta } from '../../../models/encuesta';
import { MatButtonModule } from '@angular/material/button';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faStar, IconDefinition } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-dialog-mostrar-encuesta',
  standalone: true,
  imports: [
    MatDialogModule,
    MatButtonModule,
    FontAwesomeModule,
  ],
  templateUrl: './dialog-mostrar-encuesta.component.html',
  styleUrl: '../modals.component.css'
})

export class DialogMostrarEncuestaComponent {
  readonly dialogRef = inject(MatDialogRef<DialogMostrarEncuestaComponent>);
  readonly data = inject<Encuesta>(MAT_DIALOG_DATA);
  iconStar: IconDefinition = faStar;

  volver(): void {
    this.dialogRef.close();
  }
}
