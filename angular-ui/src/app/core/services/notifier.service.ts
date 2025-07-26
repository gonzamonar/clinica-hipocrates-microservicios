import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import Swal, { SweetAlertIcon } from 'sweetalert2'; //https://sweetalert2.github.io

@Injectable({
  providedIn: 'root'
})

export class NotifierService {

  constructor(
    private _snackBar: MatSnackBar,
  ) { }


  popUpNotification(message: string, action: string = "Ocultar", duration: number = 3000){
    this.openSnackBar(message, action, duration);
  }

  successfullRegisterNotification(){
    this.openSnackBar('Registro exitoso, recibirá en instantes el email para verificar su cuenta.', "Ocultar", 5000);
  }

  private openSnackBar(message: string, action: string, duration: number) {
    this._snackBar.open(message, action, {
      duration: duration
    });
  }

  alert(title: string, text: string, icon: SweetAlertIcon = "success"){
    Swal.fire({
      title: title,
      text: text,
      icon: icon
    });
  }
}
