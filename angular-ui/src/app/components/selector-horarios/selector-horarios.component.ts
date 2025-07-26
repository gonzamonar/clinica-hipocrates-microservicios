import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges } from '@angular/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { DataHorariosService } from '../../services/data-horarios.service';
import { NotifierService } from '../../services/notifier.service';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-selector-horarios',
  standalone: true,
  imports: [
    CommonModule,
    MatTooltipModule,
    MatButtonModule
  ],
  templateUrl: './selector-horarios.component.html',
  styleUrl: './selector-horarios.component.css'
})
export class SelectorHorariosComponent implements OnChanges {
  @Input() especialista: string = '';
  @Input() horaInicio: number = 8;
  @Input() horaFin: number = 19;
  @Input() horaFinSa: number = 14;
  @Input() dias: string[] = ['Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa'];

  horas: string[] = [];
  horarios: string[] = [];
  horariosCerrado: string[] = [];

  constructor(
    private providerDataHorarios: DataHorariosService,
    private notifier: NotifierService
  ){
    for (let i=this.horaInicio; i<this.horaFin; i++){
      let hour = String(i).length == 1 ? '0' + String(i) : String(i); 
      this.horas.push(hour + ":00");
      this.horas.push(hour + ":30");
    }

    for (let i=this.horaFinSa; i<this.horaFin; i++){
      this.horariosCerrado.push("Sa - " + String(i) + ":00");
      this.horariosCerrado.push("Sa - " + String(i) + ":30");
    }
  }

  async ngOnChanges() {
    if (this.especialista != ''){
      this.providerDataHorarios.fetchOne(this.especialista)
      .then((res) => {
        if(res){
            this.horarios = res.horarios.split(',');
          }
      });
    }
  }

  seleccionarHorario(horario: string){
    if(!this.horariosCerrado.includes(horario)){
      if(this.horarios.includes(horario)){
        this.horarios.splice(this.horarios.indexOf(horario), 1);
      } else {
        this.horarios.push(horario);
      }
    }
  }

  toHorario(dia: string, hora: string){
    return dia + " - " + hora;
  }

  isSelected(horario: string){
    return this.horarios.includes(horario);
  }

  isForbidden(horario: string){
    return this.horariosCerrado.includes(horario);
  }

  filterByDay(dia: string){
    return this.horarios.filter((v) => { return v.includes(dia)}).sort((a,b) => { return a.localeCompare(b)});
  }

  guardarHorario(){
    if (this.especialista != ''){
      let stringHorarios: string = this.horarios.sort((a,b) => { return a.localeCompare(b)}).join(",");
      this.providerDataHorarios.pushOne(stringHorarios, this.especialista);
      this.notifier.popUpNotification("Horarios actualizados exitosamente.");
    } else {
      this.notifier.popUpNotification("Ha ocurrido un error realizado el guardado de horarios.");
    }
  }
}
