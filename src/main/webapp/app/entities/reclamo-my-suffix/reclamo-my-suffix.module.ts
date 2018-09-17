import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IappReclamosSharedModule } from 'app/shared';
import {
    ReclamoMySuffixComponent,
    ReclamoMySuffixDetailComponent,
    ReclamoMySuffixUpdateComponent,
    ReclamoMySuffixDeletePopupComponent,
    ReclamoMySuffixDeleteDialogComponent,
    ReclamoMySuffixFinalizarPopupComponent,
    ReclamoMySuffixFinalizarDialogComponent,
    reclamoRoute,
    reclamoPopupRoute
} from './';

const ENTITY_STATES = [...reclamoRoute, ...reclamoPopupRoute];

@NgModule({
    imports: [IappReclamosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReclamoMySuffixComponent,
        ReclamoMySuffixDetailComponent,
        ReclamoMySuffixUpdateComponent,
        ReclamoMySuffixDeleteDialogComponent,
        ReclamoMySuffixDeletePopupComponent,
        ReclamoMySuffixFinalizarDialogComponent,
        ReclamoMySuffixFinalizarPopupComponent
    ],
    entryComponents: [
        ReclamoMySuffixComponent,
        ReclamoMySuffixUpdateComponent,
        ReclamoMySuffixDeleteDialogComponent,
        ReclamoMySuffixDeletePopupComponent,
        ReclamoMySuffixFinalizarDialogComponent,
        ReclamoMySuffixFinalizarPopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IappReclamosReclamoMySuffixModule {}
