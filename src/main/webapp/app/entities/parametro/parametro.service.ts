import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParametro } from 'app/shared/model/parametro.model';

type EntityResponseType = HttpResponse<IParametro>;
type EntityArrayResponseType = HttpResponse<IParametro[]>;

@Injectable({ providedIn: 'root' })
export class ParametroService {
    private resourceUrl = SERVER_API_URL + 'api/parametros';

    constructor(private http: HttpClient) {}

    create(parametro: IParametro): Observable<EntityResponseType> {
        return this.http.post<IParametro>(this.resourceUrl, parametro, { observe: 'response' });
    }

    update(parametro: IParametro): Observable<EntityResponseType> {
        return this.http.put<IParametro>(this.resourceUrl, parametro, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IParametro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParametro[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
