import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPedidoMySuffix } from 'app/shared/model/pedido-my-suffix.model';

type EntityResponseType = HttpResponse<IPedidoMySuffix>;
type EntityArrayResponseType = HttpResponse<IPedidoMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class PedidoMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/pedidos';

    constructor(private http: HttpClient) {}

    create(pedido: IPedidoMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pedido);
        return this.http
            .post<IPedidoMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pedido: IPedidoMySuffix): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pedido);
        return this.http
            .put<IPedidoMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPedidoMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPedidoMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(pedido: IPedidoMySuffix): IPedidoMySuffix {
        const copy: IPedidoMySuffix = Object.assign({}, pedido, {
            fechaEntrega: pedido.fechaEntrega != null && pedido.fechaEntrega.isValid() ? pedido.fechaEntrega.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaEntrega = res.body.fechaEntrega != null ? moment(res.body.fechaEntrega) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((pedido: IPedidoMySuffix) => {
            pedido.fechaEntrega = pedido.fechaEntrega != null ? moment(pedido.fechaEntrega) : null;
        });
        return res;
    }
}
