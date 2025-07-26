
export class Especialidad {
    especialidad: string;
    urlImagen: string;

    constructor(
        especialidad: string,
        urlImagen: string,
    ) {
        this.especialidad = especialidad;
        this.urlImagen = urlImagen;
    }

    static constructorArr(arr: any): Especialidad[] {
        return arr.map((e: Especialidad) => { return new Especialidad(
            e.especialidad,
            e.urlImagen,
        ) });
    }

    static filtrarUno(arr: Especialidad[], especialidad: string): Especialidad {
        return arr.filter((e) => {return e.especialidad == especialidad})[0];
    }
}
