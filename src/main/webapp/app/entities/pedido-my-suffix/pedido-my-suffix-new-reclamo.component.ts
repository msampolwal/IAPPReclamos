import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';
import { ReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';
import { ReclamoMySuffixService } from 'app/entities/reclamo-my-suffix/reclamo-my-suffix.service';
import { IPedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';
import { PedidoMySuffixService } from 'app/entities/pedido-my-suffix';
import { ITipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';
import { TipoReclamoMySuffixService } from 'app/entities/tipo-reclamo-my-suffix';

@Component({
    selector: 'jhi-pedido-my-suffix-new-reclamo',
    templateUrl: './pedido-my-suffix-new-reclamo.component.html'
})
export class PedidoMySuffixNewReclamoComponent implements OnInit {
    reclamo: IReclamoMySuffix;
    pedido: IPedidoMySuffix;

    tiporeclamos: ITipoReclamoMySuffix[];
    tiporeclamo: ITipoReclamoMySuffix;

    constructor(
        private activatedRoute: ActivatedRoute,
        private reclamoService: ReclamoMySuffixService,
        private tipoReclamoService: TipoReclamoMySuffixService,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.reclamo = new ReclamoMySuffix();
        this.activatedRoute.data.subscribe(({ pedido }) => {
            this.reclamo.pedidoId = pedido.id;
            this.pedido = pedido;
        });
        this.tipoReclamoService.query().subscribe(
            (res: HttpResponse<ITipoReclamoMySuffix[]>) => {
                this.tiporeclamos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    save() {
        this.tipoReclamoService.find(this.reclamo.tipoId).subscribe(
            (res: HttpResponse<ITipoReclamoMySuffix>) => {
                this.tiporeclamo = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.reclamo.notificaLogistica = this.tiporeclamo.notificaALogistica;
        this.subscribeToSaveResponse(this.reclamoService.create(this.reclamo));
    }

    private onSaveSuccess() {
        this.previousState();
    }

    private onSaveError() {}

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReclamoMySuffix>>) {
        result.subscribe((res: HttpResponse<IReclamoMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    previousState() {
        window.history.back();
    }
}
