import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IParametro } from 'app/shared/model/parametro.model';
import { ParametroService } from './parametro.service';

@Component({
    selector: 'jhi-parametro-update',
    templateUrl: './parametro-update.component.html'
})
export class ParametroUpdateComponent implements OnInit {
    private _parametro: IParametro;
    isSaving: boolean;

    constructor(private parametroService: ParametroService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ parametro }) => {
            this.parametro = parametro;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.parametro.id !== undefined) {
            this.subscribeToSaveResponse(this.parametroService.update(this.parametro));
        } else {
            this.subscribeToSaveResponse(this.parametroService.create(this.parametro));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IParametro>>) {
        result.subscribe((res: HttpResponse<IParametro>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get parametro() {
        return this._parametro;
    }

    set parametro(parametro: IParametro) {
        this._parametro = parametro;
    }
}
