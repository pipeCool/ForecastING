import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { TransactionAng } from './transaction-ang.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class TransactionAngService {

    private resourceUrl = 'api/transactions';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(transaction: TransactionAng): Observable<TransactionAng> {
        const copy: TransactionAng = Object.assign({}, transaction);
        copy.trxDate = this.dateUtils.toDate(transaction.trxDate);
        copy.bookingDate = this.dateUtils.toDate(transaction.bookingDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(transaction: TransactionAng): Observable<TransactionAng> {
        const copy: TransactionAng = Object.assign({}, transaction);

        copy.trxDate = this.dateUtils.toDate(transaction.trxDate);

        copy.bookingDate = this.dateUtils.toDate(transaction.bookingDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<TransactionAng> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.trxDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.trxDate);
            jsonResponse.bookingDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.bookingDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].trxDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].trxDate);
            jsonResponse[i].bookingDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].bookingDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
