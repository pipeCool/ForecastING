import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { BankAccountAng } from './bank-account-ang.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class BankAccountAngService {

    private resourceUrl = 'api/bank-accounts';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(bankAccount: BankAccountAng): Observable<BankAccountAng> {
        const copy: BankAccountAng = Object.assign({}, bankAccount);
        copy.fixedAccessDate = this.dateUtils.toDate(bankAccount.fixedAccessDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(bankAccount: BankAccountAng): Observable<BankAccountAng> {
        const copy: BankAccountAng = Object.assign({}, bankAccount);

        copy.fixedAccessDate = this.dateUtils.toDate(bankAccount.fixedAccessDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<BankAccountAng> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.fixedAccessDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.fixedAccessDate);
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
            jsonResponse[i].fixedAccessDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].fixedAccessDate);
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
