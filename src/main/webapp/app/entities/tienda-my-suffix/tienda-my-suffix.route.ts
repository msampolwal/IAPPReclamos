import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TiendaMySuffix } from 'app/shared/model/tienda-my-suffix.model';
import { TiendaMySuffixService } from './tienda-my-suffix.service';
import { TiendaMySuffixComponent } from './tienda-my-suffix.component';
import { TiendaMySuffixDetailComponent } from './tienda-my-suffix-detail.component';
import { TiendaMySuffixUpdateComponent } from './tienda-my-suffix-update.component';
import { TiendaMySuffixDeletePopupComponent } from './tienda-my-suffix-delete-dialog.component';
import { ITiendaMySuffix } from 'app/shared/model/tienda-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class TiendaMySuffixResolve implements Resolve<ITiendaMySuffix> {
    constructor(private service: TiendaMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((tienda: HttpResponse<TiendaMySuffix>) => tienda.body));
        }
        return of(new TiendaMySuffix());
    }
}

export const tiendaRoute: Routes = [
    {
        path: 'tienda-my-suffix',
        component: TiendaMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'iappReclamosApp.tienda.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tienda-my-suffix/:id/view',
        component: TiendaMySuffixDetailComponent,
        resolve: {
            tienda: TiendaMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.tienda.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tienda-my-suffix/new',
        component: TiendaMySuffixUpdateComponent,
        resolve: {
            tienda: TiendaMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.tienda.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tienda-my-suffix/:id/edit',
        component: TiendaMySuffixUpdateComponent,
        resolve: {
            tienda: TiendaMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.tienda.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tiendaPopupRoute: Routes = [
    {
        path: 'tienda-my-suffix/:id/delete',
        component: TiendaMySuffixDeletePopupComponent,
        resolve: {
            tienda: TiendaMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.tienda.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
