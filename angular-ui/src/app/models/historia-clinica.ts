import { DatabaseService } from "../services/database.service";
import { DatoDinamico } from "./dato-dinamico";
import { Especialista } from "./especialista";
import { Paciente } from "./paciente";
import { Turno } from "./turno";

export class HistoriaClinica {
    nro_historia_clinica: number;
    nro_turno: number;
    paciente: string;
    especialista: string;
    altura: number;
    peso: number;
    temperatura: number;
    presion: number;
    datoDinamico1: DatoDinamico | null;
    datoDinamico2: DatoDinamico | null;
    datoDinamico3: DatoDinamico | null;

    constructor(
        nro_historia_clinica: number,
        nro_turno: number,
        paciente: string,
        especialista: string,
        altura: number,
        peso: number,
        temperatura: number,
        presion: number,
        datoDinamico1: DatoDinamico | null = null,
        datoDinamico2: DatoDinamico | null = null,
        datoDinamico3: DatoDinamico | null = null,
    ){
        this.nro_historia_clinica = nro_historia_clinica;
        this.nro_turno = nro_turno;
        this.paciente = paciente;
        this.especialista = especialista;
        this.altura = altura;
        this.peso = peso;
        this.temperatura = temperatura;
        this.presion = presion;
        this.datoDinamico1 = datoDinamico1;
        this.datoDinamico2 = datoDinamico2;
        this.datoDinamico3 = datoDinamico3;
    }

    Especialista(): Especialista {
        const DB = DatabaseService.instance;
        return Especialista.filtrarUno(DB.especialistas, this.especialista);
    }
    
    Paciente(): Paciente {
        const DB = DatabaseService.instance;
        return Paciente.filtrarUno(DB.pacientes, this.paciente);
    }
    
    Turno(): Turno {
        const DB = DatabaseService.instance;
        return Turno.filtrarUno(DB.turnos, this.nro_turno);
    }

    static constructorArr(arr: any): HistoriaClinica[] {
        return arr.map((hc: any) => { return new HistoriaClinica(
            parseInt(hc.nro_historia_clinica),
            parseInt(hc.nro_turno),
            hc.paciente,
            hc.especialista,
            parseInt(hc.altura),
            parseInt(hc.peso),
            parseInt(hc.temperatura),
            parseInt(hc.presion),
            hc.datoDinamico1 ? DatoDinamico.fromString(hc.datoDinamico1) : null,
            hc.datoDinamico2 ? DatoDinamico.fromString(hc.datoDinamico2) : null,
            hc.datoDinamico3 ? DatoDinamico.fromString(hc.datoDinamico3) : null,
        ) });
    }

    static filtrarUno(arr: HistoriaClinica[], nro_historia_clinica: number): HistoriaClinica {
        return arr.filter((hc) => { return hc.nro_historia_clinica == nro_historia_clinica })[0];
    }

    static filtrarPorTurno(arr: HistoriaClinica[], nro_turno: number): HistoriaClinica {
        return arr.filter((hc) => { return hc.nro_turno == nro_turno })[0];
    }

    static filtrarPorPaciente(arr: HistoriaClinica[], paciente: string): HistoriaClinica[] {
        return arr.filter((hc) => { return hc.paciente == paciente });
    }

    static filtrarPorEspecialista(arr: HistoriaClinica[], especialista: string): HistoriaClinica[] {
        return arr.filter((hc) => { return hc.especialista == especialista });
    }

    includes(str: string){
        let includes: boolean = false;
        let searchStr: string = str.toLocaleLowerCase();
        includes ||= ['peso', 'altura', 'temperatura', 'presion', 'presión'].map((str) => { return str.includes(searchStr)}).reduce((a,b) => { return a || b }, false);

        includes ||= this.peso.toLocaleString().includes(searchStr);
        includes ||= this.altura.toLocaleString().includes(searchStr);
        includes ||= this.temperatura.toLocaleString().includes(searchStr);
        includes ||= this.presion.toLocaleString().includes(searchStr);

        if (this.datoDinamico1){
            includes ||= this.datoDinamico1.includes(searchStr);
        }
        if (this.datoDinamico2){
            includes ||= this.datoDinamico2.includes(searchStr);
        }
        if (this.datoDinamico3){
            includes ||= this.datoDinamico3.includes(searchStr);
        }

        return includes;
    }
}

