import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { MainAccountAng } from './main-account-ang.model';
import { MainAccountAngService } from './main-account-ang.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-main-account-ang',
    templateUrl: './main-account-ang.component.html'
})
export class MainAccountAngComponent implements OnInit, OnDestroy {
mainAccounts: MainAccountAng[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mainAccountService: MainAccountAngService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.mainAccountService.query().subscribe(
            (res: Response) => {
                this.mainAccounts = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMainAccounts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MainAccountAng) {
        return item.id;
    }
    registerChangeInMainAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('mainAccountListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
