
export class DatoDinamico {
    clave: string;
    valor: string;

    constructor(
        clave: string,
        valor: string,
    ){
        this.clave = clave;
        this.valor = valor;
    }

    static fromString(dato: string): DatoDinamico {
        let campos: string[] = dato.split(' : ', 2);
        return new DatoDinamico(campos[0], campos[1]);
    }

    toString(): string {
        return this.clave + ' : ' + this.valor;
    }

    includes(str: string): boolean {
        let strLower = str.toLocaleLowerCase();
        return this.clave.toLocaleLowerCase().includes(strLower) || this.valor.toLocaleLowerCase().includes(strLower);
    }
}
