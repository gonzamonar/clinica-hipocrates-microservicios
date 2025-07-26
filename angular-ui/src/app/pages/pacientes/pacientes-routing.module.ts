import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PacientesComponent } from './pacientes.component';

const routes: Routes = [
  {
    path: '',
    component: PacientesComponent,
    data: { animation: 'routeAnimations' }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PacientesRoutingModule { }
