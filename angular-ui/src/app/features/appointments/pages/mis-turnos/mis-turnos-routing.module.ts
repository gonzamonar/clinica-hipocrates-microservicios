import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MisTurnosComponent } from './mis-turnos.component';

const routes: Routes = [
  {
    path: '',
    component: MisTurnosComponent,
    data: { animation: 'routeAnimations' }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MisTurnosRoutingModule { }
