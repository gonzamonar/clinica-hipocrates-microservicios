import { DatabaseService } from "../services/database.service";
import { Usuario } from "./usuario";

export class Log {
    fecha: Date;
    usuario: string;

    constructor(
        fecha: Date,
        usuario: string,
    ) {
        this.fecha = fecha;
        this.usuario = usuario;
    }

    Usuario(): Usuario {
        const DB = DatabaseService.instance;
        return Usuario.filtrarUno(DB.usuarios, this.usuario);
    }

    static constructorArr(arr: any[]): Log[] {
        return arr.map((e) => { return new Log(e.fecha, e.usuario); });
    }
}
