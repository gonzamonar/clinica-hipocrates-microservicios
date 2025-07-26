import { AfterViewChecked, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DataUsuariosService } from '../../../../services/data-usuarios.service';
import { Especialista } from '../../../../shared/models/especialista';
import { DatabaseService } from '../../../../services/database.service';
import { Especialidad } from '../../../../shared/models/especialidad';

@Component({
  selector: 'app-especialidades',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './especialidades.component.html',
  styleUrl: './especialidades.component.css'
})
export class EspecialidadesComponent implements AfterViewChecked {
  assetsDir: string = '/assets/img/especialidades/';
  dataEspecialidades!: Especialidad[];

  constructor (
    private DB: DatabaseService,
    private dataProviderUsuarios: DataUsuariosService
  ){ }
  
  ngAfterViewChecked(): void {
    this.dataProviderUsuarios.fetchAll().subscribe(
      (res) => {
        if (res){
          let especialidades = Especialista.getEspecialidades(Especialista.filtrarHabilitados(Especialista.constructorArr(res)));
          especialidades = [...new Set(especialidades)];
          this.dataEspecialidades = this.DB.especialidades.filter((e: Especialidad) => { return especialidades.includes(e.especialidad) });
        }
    });
  }
}
