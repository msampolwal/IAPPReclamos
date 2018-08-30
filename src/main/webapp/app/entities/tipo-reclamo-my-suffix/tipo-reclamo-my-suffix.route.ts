import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { TipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';
import { TipoReclamoMySuffixService } from './tipo-reclamo-my-suffix.service';
import { TipoReclamoMySuffixComponent } from './tipo-reclamo-my-suffix.component';
import { TipoReclamoMySuffixDetailComponent } from './tipo-reclamo-my-suffix-detail.component';
import { TipoReclamoMySuffixUpdateComponent } from './tipo-reclamo-my-suffix-update.component';
import { TipoReclamoMySuffixDeletePopupComponent } from './tipo-reclamo-my-suffix-delete-dialog.component';
import { ITipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class TipoReclamoMySuffixResolve implements Resolve<ITipoReclamoMySuffix> {
    constructor(private service: TipoReclamoMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((tipoReclamo: HttpResponse<TipoReclamoMySuffix>) => tipoReclamo.body));
        }
        return of(new TipoReclamoMySuffix());
    }
}

export const tipoReclamoRoute: Routes = [
    {
        path: 'tipo-reclamo-my-suffix',
        component: TipoReclamoMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'iappReclamosApp.tipoReclamo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-reclamo-my-suffix/:id/view',
        component: TipoReclamoMySuffixDetailComponent,
        resolve: {
            tipoReclamo: TipoReclamoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.tipoReclamo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-reclamo-my-suffix/new',
        component: TipoReclamoMySuffixUpdateComponent,
        resolve: {
            tipoReclamo: TipoReclamoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.tipoReclamo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'tipo-reclamo-my-suffix/:id/edit',
        component: TipoReclamoMySuffixUpdateComponent,
        resolve: {
            tipoReclamo: TipoReclamoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.tipoReclamo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoReclamoPopupRoute: Routes = [
    {
        path: 'tipo-reclamo-my-suffix/:id/delete',
        component: TipoReclamoMySuffixDeletePopupComponent,
        resolve: {
            tipoReclamo: TipoReclamoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.tipoReclamo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
