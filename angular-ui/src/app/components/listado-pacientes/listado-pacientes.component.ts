import { Component, OnInit } from '@angular/core';
import { CardPacienteComponent } from "../card-paciente/card-paciente.component";
import { DatabaseService } from '../../services/database.service';
import { Paciente } from '../../models/paciente';
import { Especialista } from '../../models/especialista';
import { SessionService } from '../../services/session.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listado-pacientes',
  standalone: true,
  imports: [
    CommonModule,
    CardPacienteComponent,
  ],
  templateUrl: './listado-pacientes.component.html',
  styleUrl: './listado-pacientes.component.css'
})
export class ListadoPacientesComponent implements OnInit {
  especialista: Especialista | null = null;

  constructor(
    public DB: DatabaseService,
    private session: SessionService,
  ){ }

  ngOnInit(): void {
    if (this.session.isSpecialistLevelSession() && this.session.data){
      this.especialista = <Especialista> this.session.data;
    }
  }

  getPacientes(): Paciente[] {
    let pacientes: Paciente[] = [];
    if (this.especialista) {
      let filtered = this.DB.turnos.filter((t) => { return t.especialista == this.especialista?.email });
      let mapped = filtered.map((t) => { return t.paciente });
      let set = [...new Set(mapped)];
      pacientes = Paciente.filtrarPorEmails(this.DB.pacientes, set);
    }

    return pacientes;
  }
}
