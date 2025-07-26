import { Injectable } from '@angular/core';
import { Firestore, addDoc, collection, collectionData, getDocs, limit, orderBy, query, updateDoc, where } from '@angular/fire/firestore';
import { take } from 'rxjs';
import { Turno } from '../models/turno';
import { NotifierService } from './notifier.service';
import { Estado } from '../models/enums/estado';
import { Comentario } from '../models/comentario';
import { DataComentariosService } from './data-comentarios.service';
import { LoaderService } from './loader.service';

@Injectable({
  providedIn: 'root'
})

export class DataTurnosService {
  private TABLE: string = 'turnos';
  private nextId: number = 0;

  constructor(
    private firestore: Firestore,
    private notifier: NotifierService,
    private loader: LoaderService,
  ) {
    this.getNextId()
  }

  async pushOne(turno: Turno): Promise<boolean> {
    this.loader.setLoading(true);
    let success = false;
    let dataCollection = collection(this.firestore, this.TABLE);

    if (this.nextId != 0){
      let status = await addDoc(dataCollection, {
        'nro_turno': this.nextId,
        'estado': turno.estado,
        'especialista': turno.especialista,
        'especialidad': turno.especialidad,
        'paciente': turno.paciente,
        'dia': turno.dia,
        'hora': turno.hora,
        'nro_comentario': turno.nro_comentario,
        'nro_review': turno.nro_review,
        'nro_encuesta': turno.nro_encuesta,
        'calificacion': turno.calificacion,
      });
      
      if (status){
        success = true;
        this.nextId += 1;
      }
    }
    this.loader.setLoading(false);
    return success;
  }

  async cancelarTurno(turno: Turno): Promise<void> {
    this.updateStatus(turno, Estado.Cancelado);
  }

  async aceptarTurno(turno: Turno): Promise<void> {
    this.updateStatus(turno, Estado.Aceptado);
  }

  async rechazarTurno(turno: Turno): Promise<void> {
    this.updateStatus(turno, Estado.Rechazado);
  }

  async finalizarTurno(turno: Turno): Promise<void> {
    this.updateStatus(turno, Estado.Realizado);
  }

  async calificarTurno(turno: Turno, calificacion: number): Promise<void> {
    turno.calificacion = calificacion;
    this.updateCalificacionTurno(turno);
  }

  private async updateStatus(turno: Turno, estado: string): Promise<void> {
    this.loader.setLoading(true);
    let col = collection(this.firestore, this.TABLE);
    const fetchQuery = query(
      col, 
      where("nro_turno", "==", turno.nro_turno),
      limit(1),
    );

    const querySnapshot = await getDocs(fetchQuery);
    querySnapshot.forEach((doc) => {
      updateDoc(doc.ref, {
        'estado': estado,
      });
      
      this.notifier.popUpNotification("Estado del turno actualizado exitosamente.")
    });
    this.loader.setLoading(false);
  }

  private async updateCalificacionTurno(turno: Turno): Promise<void> {
    this.loader.setLoading(true);
    let col = collection(this.firestore, this.TABLE);
    const fetchQuery = query(
      col, 
      where("nro_turno", "==", turno.nro_turno),
      limit(1),
    );

    const querySnapshot = await getDocs(fetchQuery);
    querySnapshot.forEach((doc) => {
      updateDoc(doc.ref, {
        'calificacion': turno.calificacion,
      });
    });
    this.loader.setLoading(false);
  }

  public async updateTurnoNroEncuesta(nro_turno: number, nro_encuesta: number): Promise<void> {
    this.loader.setLoading(true);
    let col = collection(this.firestore, this.TABLE);
    const fetchQuery = query(
      col, 
      where("nro_turno", "==", nro_turno),
      limit(1),
    );

    const querySnapshot = await getDocs(fetchQuery);
    querySnapshot.forEach((doc) => {
      updateDoc(doc.ref, {
        'nro_encuesta': nro_encuesta,
      });
    });
    this.loader.setLoading(false);
  }

  public async updateTurnoNroComentario(nro_turno: number, nro_comentario: number): Promise<void> {
    this.loader.setLoading(true);
    let col = collection(this.firestore, this.TABLE);
    const fetchQuery = query(
      col, 
      where("nro_turno", "==", nro_turno),
      limit(1),
    );

    const querySnapshot = await getDocs(fetchQuery);
    querySnapshot.forEach((doc) => {
      updateDoc(doc.ref, {
        'nro_comentario': nro_comentario,
      });
    });
    this.loader.setLoading(false);
  }

  public async updateTurnoNroReview(nro_turno: number, nro_review: number): Promise<void> {
    this.loader.setLoading(true);
    let col = collection(this.firestore, this.TABLE);
    const fetchQuery = query(
      col, 
      where("nro_turno", "==", nro_turno),
      limit(1),
    );

    const querySnapshot = await getDocs(fetchQuery);
    querySnapshot.forEach((doc) => {
      updateDoc(doc.ref, {
        'nro_review': nro_review,
      });
    });
    this.loader.setLoading(false);
  }

  public async updateTurnoNroHistoriaClinica(nro_turno: number, nro_historia_clinica: number): Promise<void> {
    this.loader.setLoading(true);
    let col = collection(this.firestore, this.TABLE);
    const fetchQuery = query(
      col, 
      where("nro_turno", "==", nro_turno),
      limit(1),
    );

    const querySnapshot = await getDocs(fetchQuery);
    querySnapshot.forEach((doc) => {
      updateDoc(doc.ref, {
        'nro_historia_clinica': nro_historia_clinica,
      });
    });
    this.loader.setLoading(false);
  }

  fetchAll(){
    let col = collection(this.firestore, this.TABLE);
    const sortedQuery = query(
      col,
      orderBy('nro_turno', 'asc')
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
          this.nextId =  Math.max(...response.map((e: any) => { return parseInt(e.nro_turno) })) + 1;
        }
    });
  }
}
