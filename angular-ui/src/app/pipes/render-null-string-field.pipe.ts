import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'renderNullStringField',
  standalone: true
})
export class RenderNullStringFieldPipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return value == null || value == undefined ? "-" : value ;
  }

}
