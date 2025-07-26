import { Injectable } from '@angular/core';
import { Firestore, addDoc, collection, collectionData, orderBy, query } from '@angular/fire/firestore';
import { take } from 'rxjs';
import { DataTurnosService } from './data-turnos.service';
import { Encuesta } from '../models/encuesta';
import { LoaderService } from './loader.service';

@Injectable({
  providedIn: 'root'
})
export class DataEncuestasService {
  private TABLE: string = 'encuestas';
  private nextId: number = 0;

  constructor(
    private firestore: Firestore,
    private providerDataTurnos: DataTurnosService,
    private loader: LoaderService
  ) {
    this.getNextId()
  }

  async pushOne(encuesta: Encuesta): Promise<boolean> {
    this.loader.setLoading(true);
    let success = false;
    let dataCollection = collection(this.firestore, this.TABLE);

    if (this.nextId != 0){
      encuesta.nro_encuesta = this.nextId;

      let status = await addDoc(dataCollection, {
        'nro_encuesta': encuesta.nro_encuesta,
        'nro_turno': encuesta.nro_turno,
        'calificacion_pagina': encuesta.calificacion_pagina,
        'comentario_pagina': encuesta.comentario_pagina,
        'calificacion_especialista': encuesta.calificacion_especialista,
        'comentario_especialista': encuesta.comentario_especialista,
        'calificacion_turno': encuesta.calificacion_turno,
        'comentario_turno': encuesta.comentario_turno,
      });
      
      if (status){
        success = true;
        this.providerDataTurnos.updateTurnoNroEncuesta(encuesta.nro_turno, encuesta.nro_encuesta);
        this.nextId += 1;
      }
    }
    this.loader.setLoading(false);
    return success;
  }

  fetchAll(){
    let col = collection(this.firestore, this.TABLE);
    const sortedQuery = query(
      col,
      orderBy('nro_encuesta', 'asc')
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
          this.nextId =  Math.max(...response.map((e: any) => { return parseInt(e.nro_encuesta) })) + 1;
        }
    });
  }
}
