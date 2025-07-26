
export class Encuesta {
    nro_encuesta: number;
    nro_turno: number;
    calificacion_pagina: number;
    comentario_pagina: string;
    calificacion_especialista: number;
    comentario_especialista: string;
    calificacion_turno: number;
    comentario_turno: string;

    constructor(
        nro_encuesta: number,
        nro_turno: number,
        calificacion_pagina: number,
        comentario_pagina: string,
        calificacion_especialista: number,
        comentario_especialista: string,
        calificacion_turno: number,
        comentario_turno: string,
    ){
        this.nro_encuesta = nro_encuesta;
        this.nro_turno = nro_turno;
        this.calificacion_pagina = calificacion_pagina;
        this.comentario_pagina = comentario_pagina;
        this.calificacion_especialista = calificacion_especialista;
        this.comentario_especialista = comentario_especialista;
        this.calificacion_turno = calificacion_turno;
        this.comentario_turno = comentario_turno;
    }

    static constructorArr(arr: any): Encuesta[] {
        return arr.map((c: any) => { return new Encuesta(
            c.nro_encuesta,
            c.nro_turno,
            c.calificacion_pagina,
            c.comentario_pagina,
            c.calificacion_especialista,
            c.comentario_especialista,
            c.calificacion_turno,
            c.comentario_turno,
        ) });
    }

    static filtrarUno(arr: Encuesta[], nro_encuesta: number): Encuesta {
        return arr.filter((e: Encuesta) => {return e.nro_encuesta == nro_encuesta})[0];
    }
}
