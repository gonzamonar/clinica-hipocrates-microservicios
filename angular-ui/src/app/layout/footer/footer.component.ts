import { Component } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome'; /* https://fontawesome.com */
import { faGithub, faLinkedin } from '@fortawesome/free-brands-svg-icons';
import { IconDefinition, faEnvelope } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [
    FontAwesomeModule
  ],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  githubIcon: IconDefinition = faGithub;
  mailIcon: IconDefinition = faEnvelope;
  linkedinIcon: IconDefinition = faLinkedin;
}
