import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'toDate',
  standalone: true
})
export class ToDatePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    let date: string[] = value.split("/");
    let day: string = this.addZeroes(date[0]);
    let month: string = this.addZeroes(date[1]);
    let year: string = date[2];

    return day + "-" + month + "-" + year;
  }

  addZeroes(str: string){
    return str.length == 1 ? "0" + str : str ;
  }
}
