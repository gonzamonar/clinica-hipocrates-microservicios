import { Injectable } from '@angular/core';
import { FormGroup, ValidationErrors } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormViewerService {
  namesRegex: string = "[a-zA-ZáéíóúÁÉÍÓÚöÖüÜ ']*";
  numberRegex: string = "[0-9-]*";

  constructor() { }

  controlHasErrors(form: FormGroup, control: string): boolean {
    if (form != undefined && control != undefined){
      return this.isControlInvalid(form, control) && this.isControlTouched(form, control);
    } else {
      return false;
    }
  }

  isControlInvalid(form: FormGroup, control: string): boolean{
    return form.controls[control].invalid;
  }

  isControlTouched(form: FormGroup, control: string): boolean{
    return form.controls[control].dirty || form.controls[control].touched;
  }
  
  getControlErrors(form: FormGroup, control: string): ValidationErrors | null {
    return form.controls[control].errors;
  }

  getControlErrorMessage(form: FormGroup, control: string): string {
    let errors = this.getControlErrors(form, control);
    
    let errorMessage = "";

    if (errors?.['required']){
      errorMessage = "Campo obligatorio.";
    } else if (errors?.['pattern']) {
      if (errors?.['pattern'].requiredPattern == '^' + this.namesRegex + '$'){
        errorMessage = "Caracteres inválidos.";
      } else if (errors?.['pattern'].requiredPattern == '^[0-9-]*$') {
        errorMessage = "Sólo se aceptan números."; 
      } else {
        errorMessage = "Caracteres inválidos.";
      }
    } else if (errors?.['minlength']) {
      errorMessage = "Mínimo " + errors?.['minlength'].requiredLength + " caracteres.";
    } else if (errors?.['maxlength']) {
      errorMessage = "Máximo " + errors?.['maxlength'].requiredLength + " caracteres.";
    } else if (errors?.['email']) {
      errorMessage = "Formato de email inválido.";
    } else if (errors?.['min']) {
      errorMessage = "El valor mínimo es " + errors?.['min'].min + ".";
    } else if (errors?.['max']) {
      errorMessage = "El valor máximo es " + errors?.['max'].max + ".";
    }

    return errorMessage;
  }
}
