import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IappReclamosSharedModule } from 'app/shared';
import {
    TiendaMySuffixComponent,
    TiendaMySuffixDetailComponent,
    TiendaMySuffixUpdateComponent,
    TiendaMySuffixDeletePopupComponent,
    TiendaMySuffixDeleteDialogComponent,
    tiendaRoute,
    tiendaPopupRoute
} from './';

const ENTITY_STATES = [...tiendaRoute, ...tiendaPopupRoute];

@NgModule({
    imports: [IappReclamosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TiendaMySuffixComponent,
        TiendaMySuffixDetailComponent,
        TiendaMySuffixUpdateComponent,
        TiendaMySuffixDeleteDialogComponent,
        TiendaMySuffixDeletePopupComponent
    ],
    entryComponents: [
        TiendaMySuffixComponent,
        TiendaMySuffixUpdateComponent,
        TiendaMySuffixDeleteDialogComponent,
        TiendaMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IappReclamosTiendaMySuffixModule {}
