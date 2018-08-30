import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';

@Component({
    selector: 'jhi-reclamo-my-suffix-detail',
    templateUrl: './reclamo-my-suffix-detail.component.html'
})
export class ReclamoMySuffixDetailComponent implements OnInit {
    reclamo: IReclamoMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reclamo }) => {
            this.reclamo = reclamo;
        });
    }

    previousState() {
        window.history.back();
    }
}
