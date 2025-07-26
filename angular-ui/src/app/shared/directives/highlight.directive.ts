import { Directive, ElementRef, Input, OnChanges, Renderer2 } from '@angular/core';

@Directive({
  selector: '[appHighlight]',
  standalone: true
})

export class HighlightDirective implements OnChanges {
  @Input() word: string = '';

  constructor(
    private el: ElementRef,
    private renderer: Renderer2,
  ) { }

  ngOnChanges(): void {
    let element: HTMLElement = <HTMLElement> this.el.nativeElement;
    let highlightStr = this.word;
    let content = this.getUnformattedText(element.innerHTML);

    if (!content || content == '') {
      return;
    }

    if (!highlightStr || !highlightStr.length) {
      this.renderer.setProperty(
        this.el.nativeElement,
        'innerHTML',
        content);
        return;
    }

    this.renderer.setProperty(
      this.el.nativeElement,
      'innerHTML',
      this.getFormattedText(content, highlightStr)
    );
  }

  getFormattedText(content: string, highlightStr: string) {    
    const re = new RegExp(`(${highlightStr})`, 'gi');
    return content.replace(re, `<b>$1</b>`);
  }

  getUnformattedText(content: string) {    
    const re = new RegExp(`(<b>)`, 'gi');
    const re2 = new RegExp(`(</b>)`, 'gi');
    content = content.replace(re, ``);
    return content.replace(re2, ``);
  }
}
