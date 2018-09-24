export interface IParametro {
    id?: number;
    clave?: string;
    valor?: string;
}

export class Parametro implements IParametro {
    constructor(public id?: number, public clave?: string, public valor?: string) {}
}
