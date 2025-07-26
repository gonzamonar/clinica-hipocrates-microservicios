import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { IconDefinition, faCalendar } from '@fortawesome/free-solid-svg-icons';
import { SessionService } from '../../services/session.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    MatButtonModule,
    FontAwesomeModule
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})

export class HomeComponent {
  iconTurno: IconDefinition = faCalendar;

  constructor(
    private router: Router,
    private session: SessionService
  ) {}

  redirect(){
    if (!this.session.isSessionActive()){
      this.router.navigateByUrl('/login');
    } else if (this.session.isSpecialistLevelSession()){
      this.router.navigateByUrl('/mis-turnos');
    } else {
      this.router.navigateByUrl('/solicitar-turno');
    }
  }
}
