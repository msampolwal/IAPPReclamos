import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';
import { PedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PedidoMySuffixService } from './pedido-my-suffix.service';

@Component({
    selector: 'jhi-pedido-my-suffix',
    templateUrl: './pedido-my-suffix.component.html'
})
export class PedidoMySuffixComponent implements OnInit, OnDestroy {
    currentAccount: any;
    pedidos: IPedidoMySuffix[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    criteriaFilter: any;

    constructor(
        private pedidoService: PedidoMySuffixService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.criteriaFilter = {
            dniCliente: null,
            idPedido: null,
            areSet() {
                return this.dniCliente != null || this.idPedido != null;
            },
            clear() {
                this.dniCliente = null;
                this.idPedido = null;
            }
        };
    }

    loadAll() {
        const criteria = [];
        if (this.criteriaFilter.areSet()) {
            if (this.criteriaFilter.dniCliente != null && this.criteriaFilter.dniCliente !== '') {
                criteria.push({ key: 'dniCliente.equals', value: this.criteriaFilter.dniCliente });
            }
            if (this.criteriaFilter.idPedido != null && this.criteriaFilter.idPedido !== '') {
                criteria.push({ key: 'id.equals', value: this.criteriaFilter.idPedido });
            }
        }
        this.pedidoService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                criteria
            })
            .subscribe(
                (res: HttpResponse<IPedidoMySuffix[]>) => this.paginatePedidos(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/pedido-my-suffix'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/pedido-my-suffix',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        //        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPedidos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPedidoMySuffix) {
        return item.id;
    }

    registerChangeInPedidos() {
        this.eventSubscriber = this.eventManager.subscribe('pedidoListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginatePedidos(data: IPedidoMySuffix[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.pedidos = data;
        if (data.length < 1) {
            this.onError('iappReclamosApp.pedido.sinResultado');
        }
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
