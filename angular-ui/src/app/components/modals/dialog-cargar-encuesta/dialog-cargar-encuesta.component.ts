import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormField } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ColorEstado } from '../../../models/enums/color-estado';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FormViewerService } from '../../../services/form-viewer.service';
import { MatIconModule } from '@angular/material/icon';
import { ToTitleCasePipe } from '../../../pipes/to-title-case.pipe';
import { RatingModule } from 'primeng/rating';

export interface Field {
  displayName: string,
  controlRating: string,
  controlComment: string
}

@Component({
  selector: 'app-dialog-cargar-encuesta',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormField,
    MatInputModule,
    MatTooltipModule,
    MatIconModule,
    ToTitleCasePipe,
    RatingModule,
  ],
  templateUrl: './dialog-cargar-encuesta.component.html',
  styleUrl: '../modals.component.css'
})

export class DialogCargarEncuestaComponent {
  readonly dialogRef = inject(MatDialogRef<DialogCargarEncuestaComponent>);
  readonly formViewer = inject(FormViewerService);
  readonly formBuilder = inject(FormBuilder);

  formEncuesta!: FormGroup;
  fields: Field[] = [
    { displayName: 'la pagina', controlRating: 'rating_pagina', controlComment: 'comentario_pagina' },
    { displayName: 'el especialista', controlRating: 'rating_especialista', controlComment: 'comentario_especialista' },
    { displayName: 'el turno', controlRating: 'rating_turno', controlComment: 'comentario_turno' },
  ];

  ngOnInit(): void {
    this.formEncuesta = this.formBuilder.group({
      rating_pagina: ['', [Validators.required]],
      comentario_pagina: ['', [Validators.required]],
      rating_especialista: ['', [Validators.required]],
      comentario_especialista: ['', [Validators.required]],
      rating_turno: ['', [Validators.required]],
      comentario_turno: ['', [Validators.required]],
    });
  }

  volver(): void {
    this.dialogRef.close();
  }
  
  getBtnColor(): string {
    return ColorEstado.Aceptado;
  }
}
