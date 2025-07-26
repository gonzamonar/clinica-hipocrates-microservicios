import { Injectable } from '@angular/core';
import { Firestore, addDoc, collection, collectionData, getDocs, limit, orderBy, query, updateDoc, where } from '@angular/fire/firestore';
import { take } from 'rxjs';
import { HistoriaClinica } from '../models/historia-clinica';
import { DataTurnosService } from './data-turnos.service';
import { LoaderService } from './loader.service';

@Injectable({
  providedIn: 'root'
})
export class DataHistoriaClinicaService {
  private TABLE: string = 'historiaClinica';
  private nextId: number = 0;

  constructor(
    private firestore: Firestore,
    private providerDataTurnos: DataTurnosService,
    private loader: LoaderService,
  ) {
    this.getNextId()
  }

  async pushOne(historia: HistoriaClinica): Promise<boolean> {
    this.loader.setLoading(true);
    let success = false;
    let dataCollection = collection(this.firestore, this.TABLE);

    if (this.nextId != 0){
      historia.nro_historia_clinica = this.nextId;

      let status = await addDoc(dataCollection, {
        'nro_historia_clinica': historia.nro_historia_clinica,
        'nro_turno': historia.nro_turno,
        'paciente': historia.paciente,
        'especialista': historia.especialista,
        'altura': historia.altura,
        'peso': historia.peso,
        'temperatura': historia.temperatura,
        'presion': historia.presion,
        'datoDinamico1': historia.datoDinamico1 ? historia.datoDinamico1.toString() : historia.datoDinamico1,
        'datoDinamico2': historia.datoDinamico2 ? historia.datoDinamico2.toString() : historia.datoDinamico2,
        'datoDinamico3': historia.datoDinamico3 ? historia.datoDinamico3.toString() : historia.datoDinamico3,
      });
      
      if (status){
        success = true;
        this.providerDataTurnos.updateTurnoNroHistoriaClinica(historia.nro_turno, historia.nro_historia_clinica);
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
      orderBy('nro_historia_clinica', 'asc')
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
          this.nextId =  Math.max(...response.map((e: any) => { return parseInt(e.nro_historia_clinica) })) + 1;
        }
    });
  }
}
