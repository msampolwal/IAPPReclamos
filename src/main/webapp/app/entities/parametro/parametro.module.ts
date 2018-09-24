import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IappReclamosSharedModule } from 'app/shared';
import {
    ParametroComponent,
    ParametroDetailComponent,
    ParametroUpdateComponent,
    ParametroDeletePopupComponent,
    ParametroDeleteDialogComponent,
    parametroRoute,
    parametroPopupRoute
} from './';

const ENTITY_STATES = [...parametroRoute, ...parametroPopupRoute];

@NgModule({
    imports: [IappReclamosSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ParametroComponent,
        ParametroDetailComponent,
        ParametroUpdateComponent,
        ParametroDeleteDialogComponent,
        ParametroDeletePopupComponent
    ],
    entryComponents: [ParametroComponent, ParametroUpdateComponent, ParametroDeleteDialogComponent, ParametroDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IappReclamosParametroModule {}
