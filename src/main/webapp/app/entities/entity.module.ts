import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { IappReclamosReclamoMySuffixModule } from './reclamo-my-suffix/reclamo-my-suffix.module';
import { IappReclamosPedidoMySuffixModule } from './pedido-my-suffix/pedido-my-suffix.module';
import { IappReclamosTiendaMySuffixModule } from './tienda-my-suffix/tienda-my-suffix.module';
import { IappReclamosTipoReclamoMySuffixModule } from './tipo-reclamo-my-suffix/tipo-reclamo-my-suffix.module';
import { IappReclamosParametroModule } from './parametro/parametro.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        IappReclamosReclamoMySuffixModule,
        IappReclamosPedidoMySuffixModule,
        IappReclamosTiendaMySuffixModule,
        IappReclamosTipoReclamoMySuffixModule,
        IappReclamosParametroModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class IappReclamosEntityModule {}
