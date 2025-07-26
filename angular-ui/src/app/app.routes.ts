import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { activeSessionGuard } from './guards/active-session.guard';
import { isAdminGuard } from './guards/is-admin.guard';
import { VacioComponent } from './components/vacio/vacio.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
    },
    {
        path: 'login',
        loadChildren: ()=> import('./pages/login/login-routing.module').then(m=>m.LoginRoutingModule),
        canActivate: [activeSessionGuard]
    },
    {
        path: 'registro',
        loadChildren: ()=> import('./pages/register/register-routing.module').then(m=>m.RegisterRoutingModule),
        canActivate: [activeSessionGuard]
    },
    {
        path: 'verificar-email',
        loadChildren: ()=> import('./pages/verify-email/verify-email-routing.module').then(m=>m.VerifyEmailRoutingModule),
        canActivate: [activeSessionGuard]
    },
    {
        path: 'especialidades',
        loadChildren: ()=> import('./pages/especialidades/especialidades-routing.module').then(m=>m.EspecialidadesRoutingModule),
    },
    {
        path: 'instalaciones',
        loadChildren: ()=> import('./pages/instalaciones/instalaciones-routing.module').then(m=>m.InstalacionesRoutingModule),
    },
    {
        path: 'usuarios',
        loadChildren: ()=> import('./pages/usuarios/usuarios-routing.module').then(m=>m.UsuariosRoutingModule),
    },
    {
        path: 'mis-pacientes',
        loadChildren: ()=> import('./pages/pacientes/pacientes-routing.module').then(m=>m.PacientesRoutingModule)
    },
    {
        path: 'turnos',
        loadChildren: ()=> import('./pages/turnos/turnos-routing.module').then(m=>m.TurnosRoutingModule)
    },
    {
        path: 'mis-turnos',
        loadChildren: ()=> import('./pages/mis-turnos/mis-turnos-routing.module').then(m=>m.MisTurnosRoutingModule)
    },
    {
        path: 'solicitar-turno',
        loadChildren: ()=> import('./pages/solicitar-turno/solicitar-turno-routing.module').then(m=>m.SolicitarTurnoRoutingModule)
    },
    {
        path: 'mi-perfil',
        loadChildren: ()=> import('./pages/mi-perfil/mi-perfil-routing.module').then(m=>m.MiPerfilRoutingModule)
    },
    {
        path: 'estadisticas',
        loadChildren: ()=> import('./pages/estadisticas/estadisticas-routing.module').then(m=>m.EstadisticasRoutingModule)
    },
];
