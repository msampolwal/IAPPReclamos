import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITiendaMySuffix } from 'app/shared/model/tienda-my-suffix.model';

@Component({
    selector: 'jhi-tienda-my-suffix-detail',
    templateUrl: './tienda-my-suffix-detail.component.html'
})
export class TiendaMySuffixDetailComponent implements OnInit {
    tienda: ITiendaMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tienda }) => {
            this.tienda = tienda;
        });
    }

    previousState() {
        window.history.back();
    }
}
