import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appToTitleCase]',
  standalone: true
})
export class ToTitleCaseDirective {

  constructor(
    private el: ElementRef
  ) { }

  @HostListener('input') onInput() {
    this.el.nativeElement.value = this.toTitleCase(this.el.nativeElement.value);
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
