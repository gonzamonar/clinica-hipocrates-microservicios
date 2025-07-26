import { Injectable } from '@angular/core';
import { Firestore, addDoc, collection, collectionData, orderBy, query } from '@angular/fire/firestore';
import { DatabaseService } from './database.service';
import { Especialidad } from '../models/especialidad';

@Injectable({
  providedIn: 'root'
})

export class DataEspecialidadesService {
  private TABLE: string = 'especialidades';

  constructor(
    private firestore: Firestore
  ) { }

  async pushOne(especialidad: string, especialidades: Especialidad[]): Promise<boolean> {
    let mappedEspecialidades = especialidades.map((e) => { return e.especialidad; });
    let success = false;

    if (!mappedEspecialidades.includes(especialidad)) {
      let dataCollection = collection(this.firestore,  this.TABLE);
      
      let status = await addDoc(dataCollection, {
        'especialidad': especialidad,
        'urlImagen': "general.png",
      });
      
      if (status){
        success = true;
      }
    }
    return success;
  }
  
  fetchAll(){
    let col = collection(this.firestore, this.TABLE);
    const sortedQuery = query(
      col,
      orderBy('especialidad', 'asc')
    );
    const observable = collectionData(sortedQuery);
    return observable;
  }
}
