import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { IconDefinition, faAddressCard, faBedPulse, faBusinessTime, faCalendar, faCalendarCheck, faCalendarDays, faCalendarPlus, faCalendarWeek, faHospital, faHouseMedical, faLocationDot, faPhone, faPieChart, faRightFromBracket, faRightToBracket, faStethoscope, faUsers } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SessionService } from '../../services/session.service';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    RouterModule,
    RouterOutlet,
    CommonModule,
    FontAwesomeModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements AfterViewInit {
  // Infobar Icons
  iconHorario: IconDefinition = faBusinessTime;
  iconContacto: IconDefinition = faPhone;
  iconUbicacion: IconDefinition = faLocationDot;
  // Navbar Icons
  iconInicio: IconDefinition = faHouseMedical;
  iconInstalaciones: IconDefinition = faHospital;
  iconEspecialidades: IconDefinition = faStethoscope;
  iconTurnos: IconDefinition = faCalendarWeek;
  iconUsuarios: IconDefinition = faUsers;
  iconPacientes: IconDefinition = faBedPulse;
  iconMisTurnos: IconDefinition = faCalendar;
  iconSolicitarTurno: IconDefinition = faCalendarPlus;
  iconMiPerfil: IconDefinition = faAddressCard;
  iconEstadisticas: IconDefinition = faPieChart;
  iconLogin: IconDefinition = faRightToBracket;
  iconLogout: IconDefinition = faRightFromBracket;

  @ViewChild('spot') spot!: ElementRef;
  @ViewChild('navDiv') navDiv!: ElementRef;

  fixed: boolean = false;
  spaceHeight: number = 0;

  constructor(
    public session: SessionService,
    public login: LoginService
  ){ }

  ngAfterViewInit(): void {
    const options = {
      rootMargin: '0px',
      threshold: 1.0
    }

    const callback: IntersectionObserverCallback = (entries: any, observer: IntersectionObserver) => {
      entries.forEach((entry: any) => {
        this.fixed = !entry.isIntersecting;
        if (this.fixed){
          this.spaceHeight = this.navDiv.nativeElement.offsetHeight;
        } else {
          this.spaceHeight = 0;
        }
      });
    }

    // @if = Solution for server side "ERROR ReferenceError: IntersectionObserver is not defined"
    if (typeof IntersectionObserver !== 'undefined') {
      let observer = new IntersectionObserver(callback, options);
      observer.observe(this.spot.nativeElement);
    }
  }

  cerrarSesion(){
    this.login.requireSignOut();
  }

  getUsername(){
    let username = '';
    if (this.session.data){
      username = this.session.data.nombre + " " + this.session.data.apellido;
    }
    return username;
  }

  anonImg(){
    return "../../../assets/img/navbar/user.png";
  }
}
