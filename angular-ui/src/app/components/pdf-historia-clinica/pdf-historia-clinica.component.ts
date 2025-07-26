import { CommonModule } from '@angular/common';
import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

import { DatabaseService } from '../../services/database.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { HistoriaClinica } from '../../models/historia-clinica';
import { RenderNullStringFieldPipe } from '../../pipes/render-null-string-field.pipe';
import { PdfMakerService } from '../../services/pdf-maker.service';
import { Paciente } from '../../models/paciente';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFilePdf, IconDefinition } from '@fortawesome/free-solid-svg-icons';

export interface PdfDialogData {
  dataSource: MatTableDataSource<HistoriaClinica>;
  paciente: Paciente;
  especialidad: string;
}

@Component({
  selector: 'app-pdf-historia-clinica',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatDialogModule,
    MatTableModule,
    RenderNullStringFieldPipe,
    FontAwesomeModule
  ],
  templateUrl: './pdf-historia-clinica.component.html',
  styleUrl: './pdf-historia-clinica.component.css'
})
export class PdfHistoriaClinicaComponent {
  readonly dialogRef = inject(MatDialogRef<PdfHistoriaClinicaComponent>);
  readonly data = inject<PdfDialogData>(MAT_DIALOG_DATA);
  iconPdf: IconDefinition = faFilePdf;
  displayedColumns: string[] = ['fecha', 'hora', 'especialista', 'especialidad', 'altura', 'peso', 'temperatura', 'presion', 'datoDinamico1', 'datoDinamico2', 'datoDinamico3'];
  fecha: Date = new Date();

  constructor(
    public DB: DatabaseService,
    private pdf: PdfMakerService,
  ) { }

  @ViewChild('pdfData') pdfData!: ElementRef;

  public downloadPDF(): void {
    this.pdf.createPDF(this.pdfData.nativeElement, 'historia_clinica_' + this.data.paciente.fullName())
    .then(
      () => {
        this.dialogRef.close();
    });
  }
}
