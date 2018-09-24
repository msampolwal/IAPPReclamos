import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParametro } from 'app/shared/model/parametro.model';

@Component({
    selector: 'jhi-parametro-detail',
    templateUrl: './parametro-detail.component.html'
})
export class ParametroDetailComponent implements OnInit {
    parametro: IParametro;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parametro }) => {
            this.parametro = parametro;
        });
    }

    previousState() {
        window.history.back();
    }
}
