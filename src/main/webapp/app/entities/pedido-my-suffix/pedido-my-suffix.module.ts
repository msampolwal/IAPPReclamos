import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IappReclamosSharedModule } from 'app/shared';
import {
    PedidoMySuffixComponent,
    PedidoMySuffixDetailComponent,
    PedidoMySuffixUpdateComponent,
    PedidoMySuffixDeletePopupComponent,
    PedidoMySuffixDeleteDialogComponent,
    PedidoMySuffixNewReclamoComponent,
    pedidoRoute,
    pedidoPopupRoute
} from './';

const ENTITY_STATES = [...pedidoRoute, ...pedidoPopupRoute];

@NgModule({
    imports: [IappReclamosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PedidoMySuffixComponent,
        PedidoMySuffixDetailComponent,
        PedidoMySuffixUpdateComponent,
        PedidoMySuffixDeleteDialogComponent,
        PedidoMySuffixDeletePopupComponent,
        PedidoMySuffixNewReclamoComponent
    ],
    entryComponents: [
        PedidoMySuffixComponent,
        PedidoMySuffixUpdateComponent,
        PedidoMySuffixNewReclamoComponent,
        PedidoMySuffixDeleteDialogComponent,
        PedidoMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IappReclamosPedidoMySuffixModule {}
