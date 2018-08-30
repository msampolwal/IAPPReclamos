export interface ITiendaMySuffix {
    id?: number;
    nombre?: string;
    url?: string;
}

export class TiendaMySuffix implements ITiendaMySuffix {
    constructor(public id?: number, public nombre?: string, public url?: string) {}
}
