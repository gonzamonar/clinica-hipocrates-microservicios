import { Component } from '@angular/core';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { FooterComponent } from './layout/footer/footer.component';
import { ChildrenOutletContexts, Router, RouterOutlet } from '@angular/router';
import { SpinnerComponent } from './layout/spinner/spinner.component';
import { routeAnimations } from './animations';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    NavbarComponent,
    FooterComponent,
    SpinnerComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  animations: [
    routeAnimations,
  ]
})

export class AppComponent {
  activeSessionForbiddenRoutes: string[] = ['/login', '/registro', '/verificar-email'];

  constructor(
    private contexts: ChildrenOutletContexts,
    public router: Router
  ){ }

  getRouteAnimationData() {
    return this.contexts.getContext('primary')?.route?.snapshot?.data?.['animation'];
  }
}
