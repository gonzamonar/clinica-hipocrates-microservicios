import { Component, Input, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Turno } from '../../models/turno';

@Component({
  selector: 'app-listado-turnos-tabla',
  standalone: true,
  imports: [
    MatTableModule,
  ],
  templateUrl: './listado-turnos-tabla.component.html',
  styleUrl: './listado-turnos-tabla.component.css'
})
export class ListadoTurnosTablaComponent implements OnInit {
  displayedColumns: string[] = ['nro_turno', 'estado', 'dia', 'hora', 'especialista', 'especialidad'];
  @Input() turnos: Turno[] = [];
  
  dataSource!: MatTableDataSource<Turno>;
  
  ngOnInit(): void {
    this.dataSource = new MatTableDataSource<Turno>(this.turnos);
  }
  
}
