import { Component, Injectable, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable }  from 'rxjs/Observable';

@Component({
    selector: 'jhi-infoheader',
    templateUrl: './infoheader.component.html',
    styleUrls: [
        'infoheader.css'
    ]
})

@Injectable()
export class InfoHeaderComponent  {


    constructor(private http: Http) { }

    getInfoHeader(): Observable<InfoHeaderComponent[]> {
        var options = new RequestOptions({
            headers: new Headers({
                'Accept': 'application/json'
            })
        });


        var test = this.http
            .get('./src/static/account.json', options)
            .map(resp => resp.json());

        console.log("huhu", test);

        return test;


    }


}
