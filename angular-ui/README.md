# Clínica Hipócrates
### Angular Web App: [clinica-hipocrates.web.app](https://clinica-hipocrates.web.app)
![imagen](/readme/public/home.png)

# Versionado
Este proyecto utiliza:
- [Angular CLI](https://github.com/angular/angular-cli) 17.3.4.
- [Firebase](https://firebase.google.com) (Hosting, Firestore, Auth, Storage) 17.1.0
- [Bootstrap](https://ng-bootstrap.github.io/#/home) 5.3.2
- [Angular Material](https://material.angular.io/guide/getting-started) 17.3.10
- [FontAwesome](https://docs.fontawesome.com/web/use-with/angular/) 0.14.1
- [SweetAlert2](https://sweetalert2.github.io) 11.11.1

# Tipos de Usuario
| Usuario | Requisitos |
| ------ | ------ |
| Anónimo | Usuario sin iniciar sesión. |
| Paciente | Crear cuenta Paciente en `/registro`, verificar el email e iniciar sesión en `/login`. |
| Especialista | Crear una cuenta Especialista en `/registro`, verificar el email, que un Admin lo marque como habilitado en `/usuarios` `Tab - Listado de Usuarios` e iniciar sesión. |
| Admin | Que otro admin cree una cuenta en `/usuarios` `Tab - Alta de Usuarios`, verificar el email e iniciar sesión. |

# Rutas
![imagen](/readme/navbar-img.png)

| Endpoint | Descripción | Permisos de Usuario |
| ------ | ------ | ------ |
| `/` | Página de bienvenida | Todos |
| `/instalaciones` | Página informativa sobre las instalaciones de la clínica. | Todos |
| `/especialidades` | Página informativa con las especialidades ofrecidas en la clínica.  | Todos |
| `/login` | Form para iniciar sesión | Anónimo |
| `/registro` | Form para registrarse como Paciente o Especialista | Anónimo |
| `/verificar-email` | Form para reenviar el correo de verificación | Anónimo |
| `/usuarios` | `Tab - Listado de Usuarios`: Muestra una tabla con todos los usuarios existentes.  | Admin |
| | `Tab - Alta de Usuarios`: Form para registrar usuarios de tipo Paciente, Especialista y Admin.  | Admin |
| | `Tab - Historias Clínicas`: Listado con las historias clínicas de todos los pacientes.  | Admin |
| `/turnos` | Listado con todos los turnos de la Clínica. | Admin |
| `/estadisticas` | `Tab - Estadísticas`: Página con estadísticas de los turnos de la clínica con subtabs: por especialidad, por día, turnos solicitados y turnos finalizados. | Admin |
| | `Tab - Logs de Usuarios`: Listado con todos los inicios de sesión a la página.  | Admin |
| `/solicitar-turno` | Form para solicitar un turno para sí mismo (Paciente) o para cualquier Paciente (Admin). | Admin, Paciente |
| `/mis-pacientes` | Listado los pacientes que fueron atendidos por el Especialista y el acceso a la historia clínica de cada uno. | Especialista |
| `/mis-turnos` | Listado con los turnos sacados por el Paciente / con el Especialista. | Paciente, Especialista |
| `/mi-perfil` |  `Tab - Mi Perfil`: Página con los datos y foto del usuario.  | Admin, Paciente, Especialista |
| | `Tab - Mis Horarios`: Página con el selector de horarios semanales del Especialista.  | Especialista |
| | `Tab - Historia Clínica`: Página con la historia clínica del Paciente y el acceso para descargarla en pdf.  | Paciente |


# Galería

## Páginas Públicas
![imagen](/readme/public/navbar.png)

|             Inicio              |              Login              |              Verificar Email           |
|:-------------------------------:|:-------------------------------:|:-------------------------------:|
| ![imagen](/readme/public/home.png) | ![imagen](/readme/public/login.png) | ![imagen](/readme/public/verificar-email.png) |

|       Registro            |       Registro (Form Paciente)            |        Registro (Form Especialista)            | 
|----------------------------------|----------------------------------|----------------------------------|
| ![imagen](/readme/public/registro.png) | ![imagen](/readme/public/registro-paciente.png) | ![imagen](/readme/public/registro-especialista.png) |

|             Instalaciones             |             Especialidades              |
|:----------------------------------------------------:|:---------------------------------------------:|
| ![imagen](/readme/public/instalaciones.png) | ![imagen](/readme/public/especialidades.png) |


## Páginas de Paciente
![imagen](/readme/paciente/navbar.png)

#### Mis Turnos / Solicitar Turno
|             Mis Turnos             |             Solicitar Turno              |
|:----------------------------------------------------:|:---------------------------------------------:|
| ![imagen](/readme/paciente/mis-turnos.png) | ![imagen](/readme/paciente/solicitar-turno.png) |

#### Perfil
|             Tab Mi Perfil              |              Tab Historia Clínica              |
|:-------------------------------:|:-------------------------------:|
| ![imagen](/readme/paciente/mi-perfil.png) | ![imagen](/readme/paciente/historia-clinica.png) |

|   Descargar Historia Clínica    |
|:------------------:|
| ![imagen](/readme/paciente/descargar-historia-clinica.png) |


## Páginas de Especialista
![imagen](/readme/especialista/navbar.png)

#### Mis Pacientes / Mis Turnos
|             Mis Pacientes              |             Mis Turnos             |
|:----------------------------------------------------:|:---------------------------------------------:|
| ![imagen](/readme/especialista/mispacientes.png) | ![imagen](/readme/especialista/mis-turnos.png) |


#### Perfil
|             Tab Mi Perfil              |              Tab Mis Horarios              |
|:-------------------------------:|:-------------------------------:|
| ![imagen](/readme/especialista/mi-perfil.png) | ![imagen](/readme/especialista/perfil-horarios.png) |


## Páginas de Admin
![imagen](/readme/admin/navbar.png)

#### Usuarios
|             Tab Listado de Usuarios              |              Tab Alta de Usuarios              |              Tab Historia Clínica           |
|:-------------------------------:|:-------------------------------:|:-------------------------------:|
| ![imagen](/readme/admin/usuarios--lista-usuarios.png) | ![imagen](/readme/admin/usuarios--alta-usuarios.png) | ![imagen](/readme/admin/usuarios--historia-clinica.png) |

#### Turnos
|   Turnos    |
|:------------------:|
| ![imagen](/readme/admin/turnos.png) |

#### Estadísticas
|      Tab Estadísticas -- por Especialidad              |              Tab Estadísticas -- por Día              |              Tab Estadísticas -- Solicitados           |
|:-------------------------------:|:-------------------------------:|:-------------------------------:|
| ![imagen](/readme/admin/estadisticas-especialidad.png) | ![imagen](/readme/admin/estadisticas-dia.png) | ![imagen](/readme/admin/estadisticas-solicitados.png) |

|             Tab Estadísticas -- Finalizados              |              Tab Logs              |
|:-------------------------------:|:-------------------------------:|
| ![imagen](/readme/admin/estadisticas-finalizados.png) | ![imagen](/readme/admin/estadisticas-logs.png) |
