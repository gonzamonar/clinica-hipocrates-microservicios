import { Estado } from "./enums/estado";
import { Especialista } from "./especialista";
import { Paciente } from "./paciente";
import { DatabaseService } from "../services/database.service";
import { HistoriaClinica } from "./historia-clinica";
import { Comentario } from "./comentario";
import { Encuesta } from "./encuesta";

export class Turno {
    nro_turno: number;
    estado: Estado;
    especialista: string;
    especialidad: string;
    paciente: string;
    dia: string;
    hora: string;
    nro_comentario: number | null;
    nro_review: number | null;
    nro_encuesta: number | null;
    calificacion: number | null;
    nro_historia_clinica: number | null;

    constructor(
        nro_turno: number,
        especialista: string,
        especialidad: string,
        paciente: string,
        dia: string,
        hora: string,
        estado: Estado = Estado.Pendiente,
        nro_comentario: number | null = null,
        nro_review: number | null = null,
        nro_encuesta: number | null = null,
        calificacion: number | null = null,
        nro_historia_clinica: number | null = null,
    ) {
        this.nro_turno = nro_turno;
        this.estado = estado;
        this.especialista = especialista;
        this.especialidad = especialidad;
        this.paciente = paciente;
        this.dia = dia;
        this.hora = hora;
        this.nro_comentario = nro_comentario;
        this.nro_review = nro_review;
        this.nro_encuesta = nro_encuesta;
        this.calificacion = calificacion;
        this.nro_historia_clinica = nro_historia_clinica;
    }

    Especialista(): Especialista {
        const DB = DatabaseService.instance;
        return Especialista.filtrarUno(DB.especialistas, this.especialista);
    }
    
    Paciente(): Paciente {
        const DB = DatabaseService.instance;
        return Paciente.filtrarUno(DB.pacientes, this.paciente);
    }
    
    Review(): Comentario | null {
        let review: Comentario | null = null;
        if (this.nro_review != null) {
            const DB = DatabaseService.instance;
            review = Comentario.filtrarUno(DB.comentarios, this.nro_review);
        }
        return review;
    }
    
    Comentario(): Comentario | null {
        let comentario: Comentario | null = null;
        if (this.nro_comentario != null) {
            const DB = DatabaseService.instance;
            comentario = Comentario.filtrarUno(DB.comentarios, this.nro_comentario);
        }
        return comentario;
    }
    
    Encuesta(): Encuesta | null {
        let encuesta: Encuesta | null = null;
        if (this.nro_encuesta != null) {
            const DB = DatabaseService.instance;
            encuesta = Encuesta.filtrarUno(DB.encuestas, this.nro_encuesta);
        }
        return encuesta;
    }

    HistoriaClinica(): HistoriaClinica | null {
        let hc: HistoriaClinica | null = null;
        if (this.nro_historia_clinica != null) {
            const DB = DatabaseService.instance;
            hc = HistoriaClinica.filtrarUno(DB.historiasClinicas, this.nro_historia_clinica);
        }
        return hc;
    }

    encuestaRealizada(): boolean {
        return this.nro_encuesta != null;
    }

    tieneComentarios(): boolean {
        return this.nro_comentario != null;
    }

    tieneReseñas(): boolean {
        return this.nro_review != null;
    }

    includes(str: string){
        let searchStr: string = str.toLocaleLowerCase();
        return this.especialidad.toLocaleLowerCase().includes(searchStr)
            || this.estado.toLocaleLowerCase().includes(searchStr)
            || this.dia.toLocaleLowerCase().includes(searchStr)
            || this.hora.toLocaleLowerCase().includes(searchStr)
            || this.Paciente().includes(searchStr)
            || this.Especialista().includes(searchStr)
            || this.HistoriaClinica()?.includes(searchStr);
    }

    static constructorArr(arr: any): Turno[] {
        return arr.map((i: Turno) => { return new Turno(
            i.nro_turno,
            i.especialista,
            i.especialidad,
            i.paciente,
            i.dia,
            i.hora,
            i.estado,
            i.nro_comentario,
            i.nro_review,
            i.nro_encuesta,
            i.calificacion,
            i.nro_historia_clinica,
        ) });
    }

    private toSortDate(){
        let date: string[] = this.dia.split("/");
        let day: string = Turno.addZeroes(date[0]);
        let month: string = Turno.addZeroes(date[1]);
        let year: string = date[2];
        return year + "-" + month + "-" + day;
    }

    private static addZeroes(str: string){
        return str.length == 1 ? "0" + str : str ;
    }

    static filtrarUno(arr: Turno[], nro_turno: number): Turno {
        return arr.filter((i) => {return i.nro_turno == nro_turno})[0];
    }

    static ordenarPorNroTurnoDesc(arr: Turno[]){
        return arr.sort((a, b) => { return b.nro_turno - a.nro_turno });
    }

    static ordenarPorFecha(arr: Turno[]){
        return arr.sort((a, b) => { return a.toSortDate().localeCompare(b.toSortDate()); });
    }

    static ordenarPorEspecialista(arr: Turno[]){
        return arr.sort((a, b) => { return a.especialista.localeCompare(b.especialista) });
    }

    static ordenarPorEspecialidad(arr: Turno[]){
        return arr.sort((a, b) => { return a.especialidad.localeCompare(b.especialidad) });
    }

    static contarPorEspecialista(arr: Turno[], especialista: string){
        return arr.reduce((total, value) => { return value.especialista == especialista ? total + 1 : total }, 0);
    }

    static contarPorEspecialidad(arr: Turno[], especialidad: string){
        return arr.reduce((total, value) => { return value.especialidad == especialidad ? total + 1 : total }, 0);
    }

    static contarPorDia(arr: Turno[], dia: string){
        return arr.reduce((total, value) => { return value.dia == dia ? total + 1 : total }, 0);
    }

    static contarPorEstado(arr: Turno[], estado: Estado){
        return arr.reduce((total, value) => { return value.estado == estado ? total + 1 : total }, 0);
    }

    static contarPorNoEstado(arr: Turno[], estado: Estado){
        return arr.reduce((total, value) => { return value.estado != estado ? total + 1 : total }, 0);
    }

    static filtrarPorEspecialista(arr: Turno[], especialista: string){
        return arr.filter((t: Turno) => { return t.especialista == especialista });
    }

    static filtrarPorPaciente(arr: Turno[], paciente: string){
        return arr.filter((t: Turno) => { return t.paciente == paciente });
    }

    static filtrarPorFecha(arr: Turno[], diaInicio: string, diaFin: string){
        return arr.filter((t: Turno) => { return t.toSortDate() >= diaInicio && t.toSortDate() <= diaFin });
    }

    static getEspecialidades(arr: Turno[]){
        return [... new Set(arr.map((t: Turno) => { return t.especialidad }))];
    }

    static getDias(arr: Turno[]){
        return [... new Set(arr.map((t: Turno) => { return t.dia }))];
    }

    static getEspecialistas(arr: Turno[]){
        return [... new Set(arr.map((t: Turno) => { return t.especialista }))];
    }
}
