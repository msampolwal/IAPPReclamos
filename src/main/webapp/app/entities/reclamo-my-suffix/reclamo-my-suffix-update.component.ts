import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';
import { ReclamoMySuffixService } from './reclamo-my-suffix.service';
import { IPedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';
import { PedidoMySuffixService } from 'app/entities/pedido-my-suffix';
import { ITipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';
import { TipoReclamoMySuffixService } from 'app/entities/tipo-reclamo-my-suffix';

@Component({
    selector: 'jhi-reclamo-my-suffix-update',
    templateUrl: './reclamo-my-suffix-update.component.html'
})
export class ReclamoMySuffixUpdateComponent implements OnInit {
    private _reclamo: IReclamoMySuffix;
    isSaving: boolean;

    pedidos: IPedidoMySuffix[];

    tiporeclamos: ITipoReclamoMySuffix[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private reclamoService: ReclamoMySuffixService,
        private pedidoService: PedidoMySuffixService,
        private tipoReclamoService: TipoReclamoMySuffixService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reclamo }) => {
            this.reclamo = reclamo;
        });
        this.pedidoService.query({ filter: 'reclamo-is-null' }).subscribe(
            (res: HttpResponse<IPedidoMySuffix[]>) => {
                if (!this.reclamo.pedidoId) {
                    this.pedidos = res.body;
                } else {
                    this.pedidoService.find(this.reclamo.pedidoId).subscribe(
                        (subRes: HttpResponse<IPedidoMySuffix>) => {
                            this.pedidos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoReclamoService.query().subscribe(
            (res: HttpResponse<ITipoReclamoMySuffix[]>) => {
                this.tiporeclamos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reclamo.id !== undefined) {
            this.subscribeToSaveResponse(this.reclamoService.update(this.reclamo));
        } else {
            this.subscribeToSaveResponse(this.reclamoService.create(this.reclamo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReclamoMySuffix>>) {
        result.subscribe((res: HttpResponse<IReclamoMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPedidoById(index: number, item: IPedidoMySuffix) {
        return item.id;
    }

    trackTipoReclamoById(index: number, item: ITipoReclamoMySuffix) {
        return item.id;
    }
    get reclamo() {
        return this._reclamo;
    }

    set reclamo(reclamo: IReclamoMySuffix) {
        this._reclamo = reclamo;
    }
}
