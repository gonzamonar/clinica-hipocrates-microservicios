import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EspecialidadesComponent } from './especialidades.component';

const routes: Routes = [
  {
    path: '',
    component: EspecialidadesComponent,
    data: { animation: 'routeAnimations' }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EspecialidadesRoutingModule { }
