export interface ITipoReclamoMySuffix {
    id?: number;
    descripcion?: string;
    notificaALogistica?: boolean;
    notificaATienda?: boolean;
}

export class TipoReclamoMySuffix implements ITipoReclamoMySuffix {
    constructor(public id?: number, public descripcion?: string, public notificaALogistica?: boolean, public notificaATienda?: boolean) {
        this.notificaALogistica = false;
        this.notificaATienda = false;
    }
}
