import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';

@Component({
    selector: 'jhi-pedido-my-suffix-detail',
    templateUrl: './pedido-my-suffix-detail.component.html'
})
export class PedidoMySuffixDetailComponent implements OnInit {
    pedido: IPedidoMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pedido }) => {
            this.pedido = pedido;
        });
    }

    previousState() {
        window.history.back();
    }
}
