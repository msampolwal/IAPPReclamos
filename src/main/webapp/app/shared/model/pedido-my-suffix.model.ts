import { Moment } from 'moment';

export interface IPedidoMySuffix {
    id?: number;
    fechaEntrega?: Moment;
    montoCompra?: number;
    dniCliente?: string;
    nombreCliente?: string;
    mailCliente?: string;
    idProducto?: string;
    descripcionProducto?: string;
    nombreTienda?: string;
    tiendaId?: number;
    reclamoId?: number;
}

export class PedidoMySuffix implements IPedidoMySuffix {
    constructor(
        public id?: number,
        public fechaEntrega?: Moment,
        public montoCompra?: number,
        public dniCliente?: string,
        public nombreCliente?: string,
        public mailCliente?: string,
        public idProducto?: string,
        public descripcionProducto?: string,
        public nombreTienda?: string,
        public tiendaId?: number,
        public reclamoId?: number
    ) {}
}
