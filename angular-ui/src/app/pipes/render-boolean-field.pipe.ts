import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'renderBooleanField',
  standalone: true
})
export class RenderBooleanFieldPipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    let displayText: string = "-";
    if (value != undefined && value != null){
      displayText = value ? "SÍ" : "NO";
    }
    return displayText;
  }
}
