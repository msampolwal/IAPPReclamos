import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITiendaMySuffix } from 'app/shared/model/tienda-my-suffix.model';

type EntityResponseType = HttpResponse<ITiendaMySuffix>;
type EntityArrayResponseType = HttpResponse<ITiendaMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class TiendaMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/tiendas';

    constructor(private http: HttpClient) {}

    create(tienda: ITiendaMySuffix): Observable<EntityResponseType> {
        return this.http.post<ITiendaMySuffix>(this.resourceUrl, tienda, { observe: 'response' });
    }

    update(tienda: ITiendaMySuffix): Observable<EntityResponseType> {
        return this.http.put<ITiendaMySuffix>(this.resourceUrl, tienda, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITiendaMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITiendaMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
