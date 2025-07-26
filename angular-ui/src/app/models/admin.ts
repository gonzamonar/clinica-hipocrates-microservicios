import { TipoUsuario } from "./enums/tipo-usuario";
import { Usuario } from "./usuario";

export class Admin extends Usuario {

    constructor(
        nombre: string,
        apellido: string,
        edad: number,
        dni: number,
        email: string,
        imagenPerfil: string,
        nivelUsuario: string,
    ) {
        super(nombre, apellido, edad, dni, email, imagenPerfil, nivelUsuario);  
    }

    static override constructorArr(arr: any[]): Admin[] {
        return arr
            .filter((e) => { return e['nivelUsuario'] == TipoUsuario.Admin;})
            .map((e) => { return new Admin(e.nombre, e.apellido, e.edad, e.dni, e.email, e.imagenPerfil, e.nivelUsuario); });
    }
}
