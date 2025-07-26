import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '@angular/fire/auth';

export const activeSessionGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const auth = inject(Auth);

  let sessionIsActive: boolean = auth.currentUser != null;
  
  if(sessionIsActive){
    router.navigateByUrl("/");
  }
  
  return !sessionIsActive;
};
