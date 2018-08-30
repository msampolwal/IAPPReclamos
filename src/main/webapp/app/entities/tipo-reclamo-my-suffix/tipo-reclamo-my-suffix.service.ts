import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoReclamoMySuffix } from 'app/shared/model/tipo-reclamo-my-suffix.model';

type EntityResponseType = HttpResponse<ITipoReclamoMySuffix>;
type EntityArrayResponseType = HttpResponse<ITipoReclamoMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class TipoReclamoMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/tipo-reclamos';

    constructor(private http: HttpClient) {}

    create(tipoReclamo: ITipoReclamoMySuffix): Observable<EntityResponseType> {
        return this.http.post<ITipoReclamoMySuffix>(this.resourceUrl, tipoReclamo, { observe: 'response' });
    }

    update(tipoReclamo: ITipoReclamoMySuffix): Observable<EntityResponseType> {
        return this.http.put<ITipoReclamoMySuffix>(this.resourceUrl, tipoReclamo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITipoReclamoMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITipoReclamoMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
