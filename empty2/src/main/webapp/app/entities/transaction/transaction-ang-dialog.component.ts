import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { TransactionAng } from './transaction-ang.model';
import { TransactionAngPopupService } from './transaction-ang-popup.service';
import { TransactionAngService } from './transaction-ang.service';
import { BankAccountAng, BankAccountAngService } from '../bank-account';

@Component({
    selector: 'jhi-transaction-ang-dialog',
    templateUrl: './transaction-ang-dialog.component.html'
})
export class TransactionAngDialogComponent implements OnInit {

    transaction: TransactionAng;
    authorities: any[];
    isSaving: boolean;

    bankaccounts: BankAccountAng[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private transactionService: TransactionAngService,
        private bankAccountService: BankAccountAngService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.bankAccountService.query().subscribe(
            (res: Response) => { this.bankaccounts = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.transaction.id !== undefined) {
            this.transactionService.update(this.transaction)
                .subscribe((res: TransactionAng) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.transactionService.create(this.transaction)
                .subscribe((res: TransactionAng) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: TransactionAng) {
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

    trackBankAccountById(index: number, item: BankAccountAng) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-transaction-ang-popup',
    template: ''
})
export class TransactionAngPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionPopupService: TransactionAngPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.transactionPopupService
                    .open(TransactionAngDialogComponent, params['id']);
            } else {
                this.modalRef = this.transactionPopupService
                    .open(TransactionAngDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
