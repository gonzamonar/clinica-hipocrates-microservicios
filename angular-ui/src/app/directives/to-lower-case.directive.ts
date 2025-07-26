import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appToLowerCase]',
  standalone: true
})
export class ToLowerCaseDirective {

  constructor(
    private el: ElementRef
  ) { }

  @HostListener('input') onInput() {
    this.el.nativeElement.value = this.el.nativeElement.value.toLowerCase();
  }

}
