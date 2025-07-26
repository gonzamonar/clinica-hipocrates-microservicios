import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormAltaPacienteComponent } from '../form-alta-paciente/form-alta-paciente.component';
import { FormAltaEspecialistaComponent } from '../form-alta-especialista/form-alta-especialista.component';
import { MatButtonModule } from '@angular/material/button';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IconDefinition, faBedPulse, faUserDoctor } from '@fortawesome/free-solid-svg-icons';
import { MatTooltipModule } from '@angular/material/tooltip';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-form-register',
  standalone: true,
  imports: [
    CommonModule,
    FormAltaPacienteComponent,
    FormAltaEspecialistaComponent,
    MatButtonModule,
    RouterModule,
    FontAwesomeModule,
    MatTooltipModule
  ],
  animations: [
    trigger(
      'leftTabAnimation', [
        state('visible', style({
          transform: 'translateX(0)'
        })),
        state('invisible', style({
          transform: 'translateX(-150%)'
        })),
        transition('invisible => visible', [
          animate('300ms', style({transform: 'translateX(0)'}))
        ]),
        transition('visible => invisible', [
          animate('300ms', style({transform: 'translateX(-150%)'}))
        ])
      ]
    ),
    trigger(
      'rightTabAnimation', [
        state('visible', style({
          transform: 'translateX(-100%)'
        })),
        state('invisible', style({
          transform: 'translateX(20%)'
        })),
        transition('invisible => visible', [
          animate('300ms', style({transform: 'translateX(-100%)'}))
        ]),
        transition('visible => invisible', [
          animate('300ms', style({transform: 'translateX(20%)'}))
        ])
      ]
    )
  ],
  templateUrl: './form-register.component.html',
  styleUrls: ['../form-styles.css', 'form-register.component.css']
})

export class FormRegisterComponent {
  vistaEspecialista: boolean | null = null;
  iconEspecialista: IconDefinition = faUserDoctor;
  iconPaciente: IconDefinition = faBedPulse;
}
