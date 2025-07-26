import { Injectable } from '@angular/core';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import { LoaderService } from './loader.service';

@Injectable({
  providedIn: 'root'
})
export class PdfMakerService {

  constructor(
    private loader: LoaderService,
  ) { }

  public async createPDF(data: HTMLElement, filename: string): Promise<void> {
    this.loader.setLoading(true);
    const DATA: any = data;
    const doc = new jsPDF('p', 'pt', 'a4');
    const options = {
      background: 'white',
      scale: 3
    };

    return html2canvas(DATA, options)
    .then((canvas) => {
      const img = canvas.toDataURL('image/PNG');
      // Add image Canvas to PDF
      const bufferX = 15;
      const bufferY = 15;
      const imgProps = (doc as any).getImageProperties(img);
      const pdfWidth = doc.internal.pageSize.getWidth() - 2 * bufferX;
      const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
      doc.addImage(img, 'PNG', bufferX, bufferY, pdfWidth, pdfHeight, undefined, 'FAST');
      return doc;
    })
    .then((docResult) => {
      docResult.save(`${filename}.pdf`);
      this.loader.setLoading(false);
    });
  }
}
