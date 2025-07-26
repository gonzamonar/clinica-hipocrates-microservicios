import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SolicitarTurnoComponent } from './solicitar-turno.component';

const routes: Routes = [
  {
    path: '',
    component: SolicitarTurnoComponent,
    data: { animation: 'routeAnimations' }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SolicitarTurnoRoutingModule { }
