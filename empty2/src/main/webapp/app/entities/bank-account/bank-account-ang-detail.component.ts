import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager   } from 'ng-jhipster';

import { BankAccountAng } from './bank-account-ang.model';
import { BankAccountAngService } from './bank-account-ang.service';

@Component({
    selector: 'jhi-bank-account-ang-detail',
    templateUrl: './bank-account-ang-detail.component.html'
})
export class BankAccountAngDetailComponent implements OnInit, OnDestroy {

    bankAccount: BankAccountAng;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private bankAccountService: BankAccountAngService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBankAccounts();
    }

    load(id) {
        this.bankAccountService.find(id).subscribe((bankAccount) => {
            this.bankAccount = bankAccount;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBankAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('bankAccountListModification', (response) => this.load(this.bankAccount.id));
    }
}
