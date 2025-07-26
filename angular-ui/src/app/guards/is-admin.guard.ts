import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { SessionService } from '../services/session.service';

export const isAdminGuard: CanActivateFn = (route, state) => {
  const session = inject(SessionService);
  // const router = inject(Router);

  return session.isAdminLevelSession();
};
