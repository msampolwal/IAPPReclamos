import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReclamoMySuffix } from 'app/shared/model/reclamo-my-suffix.model';

type EntityResponseType = HttpResponse<IReclamoMySuffix>;
type EntityArrayResponseType = HttpResponse<IReclamoMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class ReclamoMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/reclamos';

    constructor(private http: HttpClient) {}

    create(reclamo: IReclamoMySuffix): Observable<EntityResponseType> {
        return this.http.post<IReclamoMySuffix>(this.resourceUrl, reclamo, { observe: 'response' });
    }

    update(reclamo: IReclamoMySuffix): Observable<EntityResponseType> {
        return this.http.put<IReclamoMySuffix>(this.resourceUrl, reclamo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReclamoMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReclamoMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    finalizar(id: number): Observable<HttpResponse<any>> {
        return this.http.get<any>(`${this.resourceUrl}/finalizar/${id}`, { observe: 'response' });
    }
}
