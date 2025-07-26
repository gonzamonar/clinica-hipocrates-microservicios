import { ApplicationConfig, LOCALE_ID } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
// import { provideClientHydration } from '@angular/platform-browser';
import { initializeApp, provideFirebaseApp } from '@angular/fire/app';
import { getAuth, provideAuth } from '@angular/fire/auth';
import { getFirestore, provideFirestore } from '@angular/fire/firestore';
import { provideAnimations } from '@angular/platform-browser/animations';
import { getStorage, provideStorage } from '@angular/fire/storage';
import { MAT_TOOLTIP_DEFAULT_OPTIONS, MatTooltipDefaultOptions } from '@angular/material/tooltip';
import { provideNativeDateAdapter } from '@angular/material/core';
import { LoadingInterceptor } from './interceptors/loading.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ToTimePipe } from './pipes/to-time.pipe';
import { ToDatePipe } from './pipes/to-date.pipe';

export const myCustomTooltipDefaults: MatTooltipDefaultOptions = {
  showDelay: 0,
  hideDelay: 0,
  touchendHideDelay: 1500,
  disableTooltipInteractivity: true,
};

export const appConfig: ApplicationConfig = {
  providers: [
    // provideClientHydration(),
    ToDatePipe,
    ToTimePipe,
    provideRouter(routes), 
    provideFirebaseApp(
      () => initializeApp({
      "projectId":"clinica-hipocrates",
      "appId":"1:697040935622:web:8c225770fe8509bdf2d5c7",
      "storageBucket":"clinica-hipocrates.appspot.com",
      "apiKey":"AIzaSyC4I8Wt8sqQ244zOHozDImz7KPqiV9wOBY",
      "authDomain":"clinica-hipocrates.firebaseapp.com",
      "messagingSenderId":"697040935622"})),
    provideAuth(() => getAuth()),
    provideFirestore(() => getFirestore()),
    provideAnimations(),
    provideStorage(() => getStorage()),
    provideNativeDateAdapter(),
    { provide: LOCALE_ID, useValue: 'es-AR' },
    { provide: MAT_TOOLTIP_DEFAULT_OPTIONS, useValue: myCustomTooltipDefaults },
    { provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true },
  ]
};

