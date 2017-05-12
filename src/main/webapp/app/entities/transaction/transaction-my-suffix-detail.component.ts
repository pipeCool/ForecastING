import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { TransactionMySuffix } from './transaction-my-suffix.model';
import { TransactionMySuffixService } from './transaction-my-suffix.service';

@Component({
    selector: 'jhi-transaction-my-suffix-detail',
    templateUrl: './transaction-my-suffix-detail.component.html'
})
export class TransactionMySuffixDetailComponent implements OnInit, OnDestroy {

    transaction: TransactionMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private transactionService: TransactionMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTransactions();
    }

    load(id) {
        this.transactionService.find(id).subscribe((transaction) => {
            this.transaction = transaction;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTransactions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'transactionListModification',
            (response) => this.load(this.transaction.id)
        );
    }
}
