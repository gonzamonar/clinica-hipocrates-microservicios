import { Injectable } from '@angular/core';
import { Firestore, collectionData } from '@angular/fire/firestore';
import { addDoc, collection, orderBy, query } from 'firebase/firestore';
import { Log } from '../models/log';

@Injectable({
  providedIn: 'root'
})
export class LoggerService {
  public logDataCollection: Log[] = [];

  constructor(private firestore: Firestore) {
    this.getLogs();
  }

  createLog(user: string){
    let col = collection(this.firestore, 'logins');
    addDoc(col, {
      'fecha': new Date(),
      'usuario': user
    });
  }

  private getLogs(){
    let col = collection(this.firestore, 'logins');
    const sortedQuery = query(
      col,
      orderBy('fecha', 'desc')
    );

    const observable = collectionData(sortedQuery);
    
    observable.subscribe((response) => {
      this.logDataCollection = Log.constructorArr(response);
    });
  }
  
  public fetchAll(){
    let col = collection(this.firestore, 'logins');
    const sortedQuery = query(
      col,
      orderBy('fecha', 'desc')
    );

    const observable = collectionData(sortedQuery);
    return observable;
  }
}
