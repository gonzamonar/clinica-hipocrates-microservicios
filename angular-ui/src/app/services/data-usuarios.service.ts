import { Injectable } from '@angular/core';
import { Paciente } from '../models/paciente';
import { Admin } from '../models/admin';
import { Especialista } from '../models/especialista';
import { Firestore, addDoc, collection, collectionData, getDocs, limit, orderBy, query, updateDoc, where } from '@angular/fire/firestore';
import { ref, uploadBytes } from "@firebase/storage";
import { Storage, getDownloadURL } from "@angular/fire/storage";
import { NotifierService } from './notifier.service';


@Injectable({
  providedIn: 'root'
})

export class DataUsuariosService {
  TABLE: string = 'usuarios';

  constructor(
    private firestore: Firestore,
    private storage: Storage,
    private notifier: NotifierService
  ) { }
  
  async pushOnePaciente(paciente: Paciente, imagenPerfil: File, imagenPerfilAlt: File) {
    let dataCollection = collection(this.firestore, this.TABLE);
    let imagenPerfilUrl = await this.uploadFile(imagenPerfil);
    let imagenPerfilAltUrl = await this.uploadFile(imagenPerfilAlt);
    
    addDoc(dataCollection, {
      'nombre': paciente.nombre,
      'apellido': paciente.apellido,
      'edad': paciente.edad,
      'dni': paciente.dni,
      'email': paciente.email,
      'imagenPerfil': imagenPerfilUrl,
      'nivelUsuario': paciente.nivelUsuario,
      'obraSocial': paciente.obraSocial,
      'imagenPerfilAlt': imagenPerfilAltUrl,
    });
  }
  
  async pushOneEspecialista(especialista: Especialista, imagenPerfil: File) {
    let dataCollection = collection(this.firestore, this.TABLE);
    let imagenPerfilUrl = await this.uploadFile(imagenPerfil);
    console.log(especialista);
    
    addDoc(dataCollection, {
      'nombre': especialista.nombre,
      'apellido': especialista.apellido,
      'edad': especialista.edad,
      'dni': especialista.dni,
      'email': especialista.email,
      'imagenPerfil': imagenPerfilUrl,
      'nivelUsuario': especialista.nivelUsuario,
      'especialidad': especialista.especialidad.join(','),
      'habilitado': especialista.habilitado,
    });
  }
  
  async pushOneAdmin(admin: Admin, imagenPerfil: File) {
    let dataCollection = collection(this.firestore, this.TABLE);
    let imagenPerfilUrl = await this.uploadFile(imagenPerfil);
    
    addDoc(dataCollection, {
      'nombre': admin.nombre,
      'apellido': admin.apellido,
      'edad': admin.edad,
      'dni': admin.dni,
      'email': admin.email,
      'imagenPerfil': imagenPerfilUrl,
      'nivelUsuario': admin.nivelUsuario
    });
  }

  async fetchOne(email: string): Promise<any> {
    let col = collection(this.firestore, this.TABLE);
    const fetchQuery = query(
      col, 
      where("email", "==", email),
      limit(1),
    );
    const querySnapshot = await getDocs(fetchQuery);
    return querySnapshot.docs[0].data();
  }

  async uploadFile(blob: File) {
    const storageRef = ref(this.storage, blob.name);
    const uploadTask = await uploadBytes(storageRef, blob);
    const downloadUrl = await getDownloadURL(uploadTask.ref);
    return downloadUrl;
  }

  fetchAll(){
    let col = collection(this.firestore, this.TABLE);
    const sortedQuery = query(
      col,
      orderBy('apellido', 'asc')
    );
    const observable = collectionData(sortedQuery);
    return observable;
  }

  async updateHabilitacionEspecialista(email: string, habilitacionActual: boolean) {
    let col = collection(this.firestore, this.TABLE);
    const fetchQuery = query(
      col, 
      where("email", "==", email),
      limit(1),
    );
    const querySnapshot = await getDocs(fetchQuery);
    querySnapshot.forEach((doc) => {
      updateDoc(doc.ref, {
        'habilitado': !habilitacionActual,
      });
    });

    this.notifier.popUpNotification("Se ha " + (habilitacionActual ? "deshabilitado" : "habilitado") + " el especialista.");
  }
}
