
export class Horario {
    fecha: Date;
    diaDeSemana: string;
    dia: string;
    hora: string;

    constructor(
        fecha: Date,
        diaDeSemana: string,
        dia: string,
        hora: string,
    ){
        this.fecha = fecha;
        this.diaDeSemana = diaDeSemana;
        this.dia = dia;
        this.hora = hora;
    }

    static contarDias(arr: Horario[], dia: string){
        return arr.reduce((total, value) => { return value.dia == dia ? total + 1 : total }, 0);
    }
}
