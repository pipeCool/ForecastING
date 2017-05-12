import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { TransactionMySuffix } from './transaction-my-suffix.model';
import { TransactionMySuffixPopupService } from './transaction-my-suffix-popup.service';
import { TransactionMySuffixService } from './transaction-my-suffix.service';
import { LocationMySuffix, LocationMySuffixService } from '../location';
import { BankAccount, BankAccountService } from '../bank-account';

@Component({
    selector: 'jhi-transaction-my-suffix-dialog',
    templateUrl: './transaction-my-suffix-dialog.component.html'
})
export class TransactionMySuffixDialogComponent implements OnInit {

    transaction: TransactionMySuffix;
    authorities: any[];
    isSaving: boolean;

    locations: LocationMySuffix[];

    bankaccounts: BankAccount[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private transactionService: TransactionMySuffixService,
        private locationService: LocationMySuffixService,
        private BankAccountService: BankAccountService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.locationService.query().subscribe(
            (res: Response) => { this.locations = res.json(); }, (res: Response) => this.onError(res.json()));
        this.BankAccountService.query().subscribe(
            (res: Response) => { this.bankaccounts = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.transaction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.transactionService.update(this.transaction));
        } else {
            this.subscribeToSaveResponse(
                this.transactionService.create(this.transaction));
        }
    }

    private subscribeToSaveResponse(result: Observable<TransactionMySuffix>) {
        result.subscribe((res: TransactionMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TransactionMySuffix) {
        this.eventManager.broadcast({ name: 'transactionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackLocationById(index: number, item: LocationMySuffix) {
        return item.id;
    }

    trackBankAccountById(index: number, item: BankAccount) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-transaction-my-suffix-popup',
    template: ''
})
export class TransactionMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionPopupService: TransactionMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.transactionPopupService
                    .open(TransactionMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.transactionPopupService
                    .open(TransactionMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
