export interface ITiendaMySuffix {
    id?: number;
    nombre?: string;
    url?: string;
    nombreLogistica?: string;
    urlLogistica?: string;
}

export class TiendaMySuffix implements ITiendaMySuffix {
    constructor(
        public id?: number,
        public nombre?: string,
        public url?: string,
        public nombreLogistica?: string,
        public urlLogistica?: string
    ) {}
}
