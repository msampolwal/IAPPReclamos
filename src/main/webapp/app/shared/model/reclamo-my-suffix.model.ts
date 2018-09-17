export const enum Estado {
    PENDIENTE = 'PENDIENTE',
    PENDIENTE_LOGISTICA = 'PENDIENTE_LOGISTICA',
    FINALIZADO = 'FINALIZADO'
}

export interface IReclamoMySuffix {
    id?: number;
    observacion?: string;
    estado?: Estado;
    pedidoId?: number;
    tipoNombre?: string;
    tipoId?: number;
    notificaLogistica?: boolean;
}

export class ReclamoMySuffix implements IReclamoMySuffix {
    constructor(
        public id?: number,
        public observacion?: string,
        public estado?: Estado,
        public pedidoId?: number,
        public tipoNombre?: string,
        public tipoId?: number,
        public notificaLogistica?: boolean
    ) {}
}
