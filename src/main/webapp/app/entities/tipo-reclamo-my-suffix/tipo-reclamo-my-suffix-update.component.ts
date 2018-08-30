import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';
import { TipoReclamoMySuffixService } from './tipo-reclamo-my-suffix.service';

@Component({
    selector: 'jhi-tipo-reclamo-my-suffix-update',
    templateUrl: './tipo-reclamo-my-suffix-update.component.html'
})
export class TipoReclamoMySuffixUpdateComponent implements OnInit {
    private _tipoReclamo: ITipoReclamoMySuffix;
    isSaving: boolean;

    constructor(private tipoReclamoService: TipoReclamoMySuffixService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoReclamo }) => {
            this.tipoReclamo = tipoReclamo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoReclamo.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoReclamoService.update(this.tipoReclamo));
        } else {
            this.subscribeToSaveResponse(this.tipoReclamoService.create(this.tipoReclamo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoReclamoMySuffix>>) {
        result.subscribe((res: HttpResponse<ITipoReclamoMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get tipoReclamo() {
        return this._tipoReclamo;
    }

    set tipoReclamo(tipoReclamo: ITipoReclamoMySuffix) {
        this._tipoReclamo = tipoReclamo;
    }
}
