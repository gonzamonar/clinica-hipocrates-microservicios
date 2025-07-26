import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { ENV } from '../../../../env';
import { LoginAPIRequest, LoginAPIResponse, LoginResult } from '../interfaces/Login.interfaces';
import { RegisterResult } from '../interfaces/Register.interfaces';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient
  ) { }

  async signIn(email: string, password: string): Promise<LoginResult> {
    const URL = ENV.gateway.concat("/auth/login");
    let requestBody: LoginAPIRequest = {"email": email, "pwd": password};

    try {
      const response = await firstValueFrom(
        this.http.post<LoginAPIResponse>(URL, requestBody)
      );
      return {"token": response.token, "error": false} as LoginResult;
    } catch (error) {
      return {"token": "", "error": true} as LoginResult;
    }
  }

  async register(email: string, pwd: string, sendVerificationEmail: boolean = false, loginOnRegister: boolean = true): Promise<RegisterResult> {
    let result: RegisterResult = { error: true, msg: "Failed register." };
    return result;
    //let currentUser = this.auth.currentUser;
    /*
    return createUserWithEmailAndPassword(this.auth, email, pwd)
    .then(
      async (response) => {
        if (response.user !== null){
          result = { error: false, msg: "Successfull register." };
        }

        if (sendVerificationEmail && response.user.email !== null) {
          await sendEmailVerification(response.user);
        }

        if (!loginOnRegister) {
          await this.auth.updateCurrentUser(currentUser);
        }

        return result;
      }).catch((e) => {
        result.msg = e.code;
        return result
      });
      */
  }

  async logout(): Promise<void> {
    //return signOut(this.auth);
  }

}
