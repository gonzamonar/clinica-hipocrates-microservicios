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

@Component({
  selector: 'app-dialog-cargar-historia-clinica',
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
    ToTitleCasePipe
  ],
  templateUrl: './dialog-cargar-historia-clinica.component.html',
  styleUrl: '../modals.component.css'
})

export class DialogCargarHistoriaClinicaComponent implements OnInit {

  readonly dialogRef = inject(MatDialogRef<DialogCargarHistoriaClinicaComponent>);
  readonly formViewer = inject(FormViewerService);
  readonly formBuilder = inject(FormBuilder);
  formHistoriaClinica!: FormGroup;
  fields: any[] = [
    {descr: 'altura', icon: 'height'},
    {descr: 'peso', icon: 'scale'},
    {descr: 'temperatura', icon: 'device_thermostat'},
    {descr: 'presion', icon: 'bloodtype'},
  ];

  opcionales: any[] = [
    {clave: 'dato1clave', valor: 'dato1valor'},
    {clave: 'dato2clave', valor: 'dato2valor'},
    {clave: 'dato3clave', valor: 'dato3valor'},
  ];

  ngOnInit(): void {
    this.formHistoriaClinica = this.formBuilder.group({
      altura: ['', [Validators.required, Validators.pattern(this.formViewer.numberRegex), Validators.min(20), Validators.max(500)]],
      peso: ['', [Validators.required, Validators.pattern(this.formViewer.numberRegex), Validators.min(1), Validators.max(1000)]],
      temperatura: ['', [Validators.required, Validators.pattern(this.formViewer.numberRegex), Validators.min(30), Validators.max(50)]],
      presion: ['', [Validators.required, Validators.pattern(this.formViewer.numberRegex), Validators.min(50), Validators.max(200)]],
      dato1clave: [''],
      dato1valor: [''],
      dato2clave: [''],
      dato2valor: [''],
      dato3clave: [''],
      dato3valor: [''],
    });
  }

  volver(): void {
    this.dialogRef.close();
  }
  
  getBtnColor(): string {
    return ColorEstado.Realizado;
  }
  
  controlHasErrors(control: string){
    return this.formViewer.controlHasErrors(this.formHistoriaClinica, control);
  }

  getControlErrorMessage(control: string){
    return this.formViewer.getControlErrorMessage(this.formHistoriaClinica, control);
  }
}
