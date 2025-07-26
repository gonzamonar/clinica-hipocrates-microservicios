import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'toUppercase',
  standalone: true
})

export class ToUppercasePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    if (value != undefined && value != null){
      value = value.toString().toUpperCase();
    }
    return value;
  }
}
