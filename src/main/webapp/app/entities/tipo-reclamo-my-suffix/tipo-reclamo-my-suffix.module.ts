import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IappReclamosSharedModule } from 'app/shared';
import {
    TipoReclamoMySuffixComponent,
    TipoReclamoMySuffixDetailComponent,
    TipoReclamoMySuffixUpdateComponent,
    TipoReclamoMySuffixDeletePopupComponent,
    TipoReclamoMySuffixDeleteDialogComponent,
    tipoReclamoRoute,
    tipoReclamoPopupRoute
} from './';

const ENTITY_STATES = [...tipoReclamoRoute, ...tipoReclamoPopupRoute];

@NgModule({
    imports: [IappReclamosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TipoReclamoMySuffixComponent,
        TipoReclamoMySuffixDetailComponent,
        TipoReclamoMySuffixUpdateComponent,
        TipoReclamoMySuffixDeleteDialogComponent,
        TipoReclamoMySuffixDeletePopupComponent
    ],
    entryComponents: [
        TipoReclamoMySuffixComponent,
        TipoReclamoMySuffixUpdateComponent,
        TipoReclamoMySuffixDeleteDialogComponent,
        TipoReclamoMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IappReclamosTipoReclamoMySuffixModule {}
