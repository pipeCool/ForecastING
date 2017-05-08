import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager   } from 'ng-jhipster';

import { AccountHolderAng } from './account-holder-ang.model';
import { AccountHolderAngService } from './account-holder-ang.service';

@Component({
    selector: 'jhi-account-holder-ang-detail',
    templateUrl: './account-holder-ang-detail.component.html'
})
export class AccountHolderAngDetailComponent implements OnInit, OnDestroy {

    accountHolder: AccountHolderAng;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private accountHolderService: AccountHolderAngService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAccountHolders();
    }

    load(id) {
        this.accountHolderService.find(id).subscribe((accountHolder) => {
            this.accountHolder = accountHolder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAccountHolders() {
        this.eventSubscriber = this.eventManager.subscribe('accountHolderListModification', (response) => this.load(this.accountHolder.id));
    }
}
