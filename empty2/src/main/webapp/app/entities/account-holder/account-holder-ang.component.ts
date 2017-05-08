import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, AlertService } from 'ng-jhipster';

import { AccountHolderAng } from './account-holder-ang.model';
import { AccountHolderAngService } from './account-holder-ang.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-account-holder-ang',
    templateUrl: './account-holder-ang.component.html'
})
export class AccountHolderAngComponent implements OnInit, OnDestroy {
accountHolders: AccountHolderAng[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private accountHolderService: AccountHolderAngService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.accountHolderService.query().subscribe(
            (res: Response) => {
                this.accountHolders = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAccountHolders();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AccountHolderAng) {
        return item.id;
    }
    registerChangeInAccountHolders() {
        this.eventSubscriber = this.eventManager.subscribe('accountHolderListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
