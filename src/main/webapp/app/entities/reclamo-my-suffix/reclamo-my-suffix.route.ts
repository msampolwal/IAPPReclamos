import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';
import { ReclamoMySuffixService } from './reclamo-my-suffix.service';
import { ReclamoMySuffixComponent } from './reclamo-my-suffix.component';
import { ReclamoMySuffixDetailComponent } from './reclamo-my-suffix-detail.component';
import { ReclamoMySuffixUpdateComponent } from './reclamo-my-suffix-update.component';
import { ReclamoMySuffixDeletePopupComponent } from './reclamo-my-suffix-delete-dialog.component';
import { ReclamoMySuffixFinalizarPopupComponent } from './reclamo-my-suffix-finalizar-dialog.component';
import { IReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class ReclamoMySuffixResolve implements Resolve<IReclamoMySuffix> {
    constructor(private service: ReclamoMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((reclamo: HttpResponse<ReclamoMySuffix>) => reclamo.body));
        }
        return of(new ReclamoMySuffix());
    }
}

export const reclamoRoute: Routes = [
    {
        path: 'reclamo-my-suffix',
        component: ReclamoMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'iappReclamosApp.reclamo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reclamo-my-suffix/:id/view',
        component: ReclamoMySuffixDetailComponent,
        resolve: {
            reclamo: ReclamoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.reclamo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reclamo-my-suffix/new',
        component: ReclamoMySuffixUpdateComponent,
        resolve: {
            reclamo: ReclamoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.reclamo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'reclamo-my-suffix/:id/edit',
        component: ReclamoMySuffixUpdateComponent,
        resolve: {
            reclamo: ReclamoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.reclamo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reclamoPopupRoute: Routes = [
    {
        path: 'reclamo-my-suffix/:id/delete',
        component: ReclamoMySuffixDeletePopupComponent,
        resolve: {
            reclamo: ReclamoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.reclamo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reclamo-my-suffix/:id/finalizar',
        component: ReclamoMySuffixFinalizarPopupComponent,
        resolve: {
            reclamo: ReclamoMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'iappReclamosApp.reclamo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
