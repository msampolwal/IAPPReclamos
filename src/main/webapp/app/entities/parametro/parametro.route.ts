import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Parametro } from 'app/shared/model/parametro.model';
import { ParametroService } from './parametro.service';
import { ParametroComponent } from './parametro.component';
import { ParametroDetailComponent } from './parametro-detail.component';
import { ParametroUpdateComponent } from './parametro-update.component';
import { ParametroDeletePopupComponent } from './parametro-delete-dialog.component';
import { IParametro } from 'app/shared/model/parametro.model';

@Injectable({ providedIn: 'root' })
export class ParametroResolve implements Resolve<IParametro> {
    constructor(private service: ParametroService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((parametro: HttpResponse<Parametro>) => parametro.body));
        }
        return of(new Parametro());
    }
}

export const parametroRoute: Routes = [
    {
        path: 'parametro',
        component: ParametroComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'iappReclamosApp.parametro.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parametro/:id/view',
        component: ParametroDetailComponent,
        resolve: {
            parametro: ParametroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.parametro.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parametro/new',
        component: ParametroUpdateComponent,
        resolve: {
            parametro: ParametroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.parametro.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'parametro/:id/edit',
        component: ParametroUpdateComponent,
        resolve: {
            parametro: ParametroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.parametro.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parametroPopupRoute: Routes = [
    {
        path: 'parametro/:id/delete',
        component: ParametroDeletePopupComponent,
        resolve: {
            parametro: ParametroResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.parametro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
