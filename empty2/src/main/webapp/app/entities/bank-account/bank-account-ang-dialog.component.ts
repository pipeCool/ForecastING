import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { BankAccountAng } from './bank-account-ang.model';
import { BankAccountAngPopupService } from './bank-account-ang-popup.service';
import { BankAccountAngService } from './bank-account-ang.service';
import { MainAccountAng, MainAccountAngService } from '../main-account';

@Component({
    selector: 'jhi-bank-account-ang-dialog',
    templateUrl: './bank-account-ang-dialog.component.html'
})
export class BankAccountAngDialogComponent implements OnInit {

    bankAccount: BankAccountAng;
    authorities: any[];
    isSaving: boolean;

    mainaccounts: MainAccountAng[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private bankAccountService: BankAccountAngService,
        private mainAccountService: MainAccountAngService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.mainAccountService.query().subscribe(
            (res: Response) => { this.mainaccounts = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bankAccount.id !== undefined) {
            this.bankAccountService.update(this.bankAccount)
                .subscribe((res: BankAccountAng) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.bankAccountService.create(this.bankAccount)
                .subscribe((res: BankAccountAng) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: BankAccountAng) {
        this.eventManager.broadcast({ name: 'bankAccountListModification', content: 'OK'});
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

    trackMainAccountById(index: number, item: MainAccountAng) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-bank-account-ang-popup',
    template: ''
})
export class BankAccountAngPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bankAccountPopupService: BankAccountAngPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bankAccountPopupService
                    .open(BankAccountAngDialogComponent, params['id']);
            } else {
                this.modalRef = this.bankAccountPopupService
                    .open(BankAccountAngDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
