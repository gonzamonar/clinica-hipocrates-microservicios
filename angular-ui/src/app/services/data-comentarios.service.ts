import { Injectable } from '@angular/core';
import { Firestore, addDoc, collection, collectionData, orderBy, query } from '@angular/fire/firestore';
import { take } from 'rxjs';
import { DataTurnosService } from './data-turnos.service';
import { Comentario } from '../models/comentario';
import { TipoComentario } from '../models/enums/tipo-comentario';

@Injectable({
  providedIn: 'root'
})
export class DataComentariosService {
  private TABLE: string = 'comentarios';
  private nextId: number = 0;

  constructor(
    private firestore: Firestore,
    private providerDataTurnos: DataTurnosService
  ) {
    this.getNextId()
  }

  async pushOne(comentario: Comentario): Promise<boolean> {
    let success = false;
    let dataCollection = collection(this.firestore, this.TABLE);

    if (this.nextId != 0){
      comentario.nro_comentario = this.nextId;

      let status = await addDoc(dataCollection, {
        'nro_comentario': comentario.nro_comentario,
        'nro_turno': comentario.nro_turno,
        'tipo_comentario': comentario.tipo_comentario,
        'comentador': comentario.comentador,
        'motivo': comentario.motivo,
        'comentario': comentario.comentario,
      });
      
      if (status){
        success = true;
        if (comentario.tipo_comentario == TipoComentario.Comentario){
          this.providerDataTurnos.updateTurnoNroComentario(comentario.nro_turno, comentario.nro_comentario);
        } else if (comentario.tipo_comentario == TipoComentario.Review) {
          this.providerDataTurnos.updateTurnoNroReview(comentario.nro_turno, comentario.nro_comentario);
        }
        this.nextId += 1;
      }
    }
    return success;
  }

  fetchAll(){
    let col = collection(this.firestore, this.TABLE);
    const sortedQuery = query(
      col,
      orderBy('nro_comentario', 'asc')
    );
    const observable = collectionData(sortedQuery);
    return observable;
  }

  private getNextId() {
    this.fetchAll().pipe(take(1)).subscribe(
      (response) => {
        if (response.length == 0) {
          this.nextId = 1;
        } else {
          this.nextId =  Math.max(...response.map((e: any) => { return parseInt(e.nro_comentario) })) + 1;
        }
    });
  }

  agregarComentario(comentario: Comentario){
    this.pushOne(comentario);
  }
}
