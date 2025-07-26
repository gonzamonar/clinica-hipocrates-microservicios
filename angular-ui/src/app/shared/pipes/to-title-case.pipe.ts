import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'toTitleCase',
  standalone: true
})
export class ToTitleCasePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    if (value != undefined && value != null){
      value = this.toTitleCase(value.toString());
    }
    return value;
  }

  toTitleCase(str: string) {
    return str.replace(
      /\w\S*/g,
      function(txt: string) {
        return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
      }
    );
  }
}
