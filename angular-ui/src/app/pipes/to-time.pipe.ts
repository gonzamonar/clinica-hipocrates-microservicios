import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'toTime',
  standalone: true
})
export class ToTimePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): string {
    let time: string[] = value.split(":");
    let hour: string = time[0];
    let mins: string = time[1];
    let period = parseInt(hour) > 12 ? "pm" : "am" ;
    return hour + ":" + mins + " " + period;
  }

}
