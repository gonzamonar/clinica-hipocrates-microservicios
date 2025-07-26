import { Component } from '@angular/core';
import { FormResendVerificationComponent } from '../../components/form-resend-verification/form-resend-verification.component';

@Component({
  selector: 'app-verify-email',
  standalone: true,
  imports: [
    FormResendVerificationComponent
  ],
  templateUrl: './verify-email.component.html',
  styleUrl: './verify-email.component.css'
})
export class VerifyEmailComponent {

}
