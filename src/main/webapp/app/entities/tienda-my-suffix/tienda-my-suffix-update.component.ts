import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITiendaMySuffix } from 'app/shared/model/tienda-my-suffix.model';
import { TiendaMySuffixService } from './tienda-my-suffix.service';

@Component({
    selector: 'jhi-tienda-my-suffix-update',
    templateUrl: './tienda-my-suffix-update.component.html'
})
export class TiendaMySuffixUpdateComponent implements OnInit {
    private _tienda: ITiendaMySuffix;
    isSaving: boolean;

    constructor(private tiendaService: TiendaMySuffixService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tienda }) => {
            this.tienda = tienda;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tienda.id !== undefined) {
            this.subscribeToSaveResponse(this.tiendaService.update(this.tienda));
        } else {
            this.subscribeToSaveResponse(this.tiendaService.create(this.tienda));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITiendaMySuffix>>) {
        result.subscribe((res: HttpResponse<ITiendaMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get tienda() {
        return this._tienda;
    }

    set tienda(tienda: ITiendaMySuffix) {
        this._tienda = tienda;
    }
}
