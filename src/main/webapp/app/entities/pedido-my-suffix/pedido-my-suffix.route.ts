import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';
import { PedidoMySuffixService } from './pedido-my-suffix.service';
import { PedidoMySuffixComponent } from './pedido-my-suffix.component';
import { PedidoMySuffixDetailComponent } from './pedido-my-suffix-detail.component';
import { PedidoMySuffixUpdateComponent } from './pedido-my-suffix-update.component';
import { PedidoMySuffixDeletePopupComponent } from './pedido-my-suffix-delete-dialog.component';
import { PedidoMySuffixNewReclamoComponent } from './pedido-my-suffix-new-reclamo.component';
import { IPedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class PedidoMySuffixResolve implements Resolve<IPedidoMySuffix> {
    constructor(private service: PedidoMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((pedido: HttpResponse<PedidoMySuffix>) => pedido.body));
        }
        return of(new PedidoMySuffix());
    }
}

export const pedidoRoute: Routes = [
    {
        path: 'pedido-my-suffix',
        component: PedidoMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'iappReclamosApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pedido-my-suffix/:id/view',
        component: PedidoMySuffixDetailComponent,
        resolve: {
            pedido: PedidoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pedido-my-suffix/new',
        component: PedidoMySuffixUpdateComponent,
        resolve: {
            pedido: PedidoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pedido-my-suffix/:id/edit',
        component: PedidoMySuffixUpdateComponent,
        resolve: {
            pedido: PedidoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pedido-my-suffix/:id/reclamo',
        component: PedidoMySuffixNewReclamoComponent,
        resolve: {
            pedido: PedidoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pedidoPopupRoute: Routes = [
    {
        path: 'pedido-my-suffix/:id/delete',
        component: PedidoMySuffixDeletePopupComponent,
        resolve: {
            pedido: PedidoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.pedido.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
