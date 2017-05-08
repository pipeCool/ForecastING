import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager   } from 'ng-jhipster';

import { MainAccountAng } from './main-account-ang.model';
import { MainAccountAngService } from './main-account-ang.service';

@Component({
    selector: 'jhi-main-account-ang-detail',
    templateUrl: './main-account-ang-detail.component.html'
})
export class MainAccountAngDetailComponent implements OnInit, OnDestroy {

    mainAccount: MainAccountAng;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private mainAccountService: MainAccountAngService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMainAccounts();
    }

    load(id) {
        this.mainAccountService.find(id).subscribe((mainAccount) => {
            this.mainAccount = mainAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMainAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('mainAccountListModification', (response) => this.load(this.mainAccount.id));
    }
}
