import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';
import { PedidoMySuffixService } from './pedido-my-suffix.service';
import { ITiendaMySuffix } from 'app/shared/model/tienda-my-suffix.model';
import { TiendaMySuffixService } from 'app/entities/tienda-my-suffix';
import { IReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';
import { ReclamoMySuffixService } from 'app/entities/reclamo-my-suffix';

@Component({
    selector: 'jhi-pedido-my-suffix-update',
    templateUrl: './pedido-my-suffix-update.component.html'
})
export class PedidoMySuffixUpdateComponent implements OnInit {
    private _pedido: IPedidoMySuffix;
    isSaving: boolean;

    tiendas: ITiendaMySuffix[];

    reclamos: IReclamoMySuffix[];
    fechaEntregaDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private pedidoService: PedidoMySuffixService,
        private tiendaService: TiendaMySuffixService,
        private reclamoService: ReclamoMySuffixService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pedido }) => {
            this.pedido = pedido;
        });
        this.tiendaService.query().subscribe(
            (res: HttpResponse<ITiendaMySuffix[]>) => {
                this.tiendas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.reclamoService.query().subscribe(
            (res: HttpResponse<IReclamoMySuffix[]>) => {
                this.reclamos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pedido.id !== undefined) {
            this.subscribeToSaveResponse(this.pedidoService.update(this.pedido));
        } else {
            this.subscribeToSaveResponse(this.pedidoService.create(this.pedido));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPedidoMySuffix>>) {
        result.subscribe((res: HttpResponse<IPedidoMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTiendaById(index: number, item: ITiendaMySuffix) {
        return item.id;
    }

    trackReclamoById(index: number, item: IReclamoMySuffix) {
        return item.id;
    }
    get pedido() {
        return this._pedido;
    }

    set pedido(pedido: IPedidoMySuffix) {
        this._pedido = pedido;
    }
}
