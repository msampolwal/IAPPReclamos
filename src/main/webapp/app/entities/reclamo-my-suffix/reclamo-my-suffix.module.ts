import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IappReclamosSharedModule } from 'app/shared';
import {
    ReclamoMySuffixComponent,
    ReclamoMySuffixDetailComponent,
    ReclamoMySuffixUpdateComponent,
    ReclamoMySuffixDeletePopupComponent,
    ReclamoMySuffixDeleteDialogComponent,
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
        ReclamoMySuffixDeletePopupComponent
    ],
    entryComponents: [
        ReclamoMySuffixComponent,
        ReclamoMySuffixUpdateComponent,
        ReclamoMySuffixDeleteDialogComponent,
        ReclamoMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IappReclamosReclamoMySuffixModule {}
