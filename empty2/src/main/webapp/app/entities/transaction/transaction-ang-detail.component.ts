import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager   } from 'ng-jhipster';

import { TransactionAng } from './transaction-ang.model';
import { TransactionAngService } from './transaction-ang.service';

@Component({
    selector: 'jhi-transaction-ang-detail',
    templateUrl: './transaction-ang-detail.component.html'
})
export class TransactionAngDetailComponent implements OnInit, OnDestroy {

    transaction: TransactionAng;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private transactionService: TransactionAngService,
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
        this.eventSubscriber = this.eventManager.subscribe('transactionListModification', (response) => this.load(this.transaction.id));
    }
}
