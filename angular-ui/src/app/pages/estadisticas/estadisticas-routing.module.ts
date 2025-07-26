import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EstadisticasComponent } from './estadisticas.component';

const routes: Routes = [
  {
    path: '',
    component: EstadisticasComponent,
    data: { animation: 'routeAnimations' },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EstadisticasRoutingModule { }
