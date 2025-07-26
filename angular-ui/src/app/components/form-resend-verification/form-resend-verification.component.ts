import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button'
import { RouterModule, RouterOutlet } from '@angular/router';
import { NotifierService } from '../../services/notifier.service';

@Component({
  selector: 'app-form-resend-verification',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    RouterModule,
    RouterOutlet
  ],
  templateUrl: './form-resend-verification.component.html',
  styleUrl: '../form-styles.css'
})
export class FormResendVerificationComponent {
  resendError: boolean = false;
  email: string = '';
  password: string = '';
  hide: boolean = true;
  errorMessage: string = '';
  sendingRequest: boolean = false;

  constructor (
    public login: LoginService,
    private notifier: NotifierService
  ) { }

  ResendEmail() {
    if (!this.sendingRequest){
      this.sendingRequest = true;
      this.resendError = false;

      this.login.resendEmailVerification(this.email, this.password)
      .then((res) => {
          if (res.resend) {
            this.notifier.popUpNotification(res.message);
            this.email = "";
            this.password = "";
          } else {
            this.resendError = true;
            this.errorMessage = res.message;
          }
      }).then(() => {
          this.sendingRequest = false;
      });
    }
  }
}
