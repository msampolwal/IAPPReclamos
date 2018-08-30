import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';

@Component({
    selector: 'jhi-tipo-reclamo-my-suffix-detail',
    templateUrl: './tipo-reclamo-my-suffix-detail.component.html'
})
export class TipoReclamoMySuffixDetailComponent implements OnInit {
    tipoReclamo: ITipoReclamoMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tipoReclamo }) => {
            this.tipoReclamo = tipoReclamo;
        });
    }

    previousState() {
        window.history.back();
    }
}
