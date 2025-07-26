import { Routes } from '@angular/router';
import { activeSessionGuard } from './core/guards/active-session.guard';
import { HomeComponent } from './features/public/pages/home/home.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
    },
    {
        path: 'login',
        loadChildren: ()=> import('./features/auth/pages/login/login-routing.module').then(m=>m.LoginRoutingModule),
        canActivate: [activeSessionGuard]
    },
    {
        path: 'registro',
        loadChildren: ()=> import('./features/auth/pages/register/register-routing.module').then(m=>m.RegisterRoutingModule),
        canActivate: [activeSessionGuard]
    },
    {
        path: 'verificar-email',
        loadChildren: ()=> import('./features/auth/pages/verify-email/verify-email-routing.module').then(m=>m.VerifyEmailRoutingModule),
        canActivate: [activeSessionGuard]
    },
    {
        path: 'especialidades',
        loadChildren: ()=> import('./features/public/pages/especialidades/especialidades-routing.module').then(m=>m.EspecialidadesRoutingModule),
    },
    {
        path: 'instalaciones',
        loadChildren: ()=> import('./features/public/pages/instalaciones/instalaciones-routing.module').then(m=>m.InstalacionesRoutingModule),
    },
    {
        path: 'usuarios',
        loadChildren: ()=> import('./features/users/pages/usuarios/usuarios-routing.module').then(m=>m.UsuariosRoutingModule),
    },
    {
        path: 'mis-pacientes',
        loadChildren: ()=> import('./features/users/pages/pacientes/pacientes-routing.module').then(m=>m.PacientesRoutingModule)
    },
    {
        path: 'turnos',
        loadChildren: ()=> import('./features/appointments/pages/turnos/turnos-routing.module').then(m=>m.TurnosRoutingModule)
    },
    {
        path: 'mis-turnos',
        loadChildren: ()=> import('./features/appointments/pages/mis-turnos/mis-turnos-routing.module').then(m=>m.MisTurnosRoutingModule)
    },
    {
        path: 'solicitar-turno',
        loadChildren: ()=> import('./features/appointments/pages/solicitar-turno/solicitar-turno-routing.module').then(m=>m.SolicitarTurnoRoutingModule)
    },
    {
        path: 'mi-perfil',
        loadChildren: ()=> import('./features/users/pages/mi-perfil/mi-perfil-routing.module').then(m=>m.MiPerfilRoutingModule)
    },
    {
        path: 'estadisticas',
        loadChildren: ()=> import('./features/statistics/pages/estadisticas/estadisticas-routing.module').then(m=>m.EstadisticasRoutingModule)
    },
];
