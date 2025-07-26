import { DatabaseService } from "../services/database.service";
import { TipoComentario } from "./enums/tipo-comentario";
import { Turno } from "./turno";
import { Usuario } from "./usuario";

export class Comentario {
    nro_comentario: number;
    nro_turno: number;
    tipo_comentario: TipoComentario;
    comentador: string;
    motivo: string;
    comentario: string;

    constructor(
        nro_comentario: number,
        nro_turno: number,
        tipo_comentario: TipoComentario,
        comentador: string,
        motivo: string,
        comentario: string,
    ) { 
        this.nro_comentario = nro_comentario;
        this.nro_turno = nro_turno;
        this.tipo_comentario = tipo_comentario;
        this.comentador = comentador;
        this.motivo = motivo;
        this.comentario = comentario;
    }

    static constructorArr(arr: any): Comentario[] {
        return arr.map((c: any) => { return new Comentario(
            parseInt(c.nro_comentario),
            parseInt(c.nro_turno),
            c.tipo_comentario,
            c.comentador,
            c.motivo,
            c.comentario,
        ) });
    }

    static filtrarUno(arr: Comentario[], nro_comentario: number): Comentario {
        return arr.filter((i) => {return i.nro_comentario == nro_comentario})[0];
    }

    Comentador(): Usuario {
        const DB = DatabaseService.instance;
        return Usuario.filtrarUno(DB.usuarios, this.comentador);
    }

    Turno(): Turno {
        const DB = DatabaseService.instance;
        return Turno.filtrarUno(DB.turnos, this.nro_turno);
    }
}
