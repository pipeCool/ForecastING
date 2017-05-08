import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { AccountHolderAng } from './account-holder-ang.model';
import { AccountHolderAngPopupService } from './account-holder-ang-popup.service';
import { AccountHolderAngService } from './account-holder-ang.service';
import { MainAccountAng, MainAccountAngService } from '../main-account';

@Component({
    selector: 'jhi-account-holder-ang-dialog',
    templateUrl: './account-holder-ang-dialog.component.html'
})
export class AccountHolderAngDialogComponent implements OnInit {

    accountHolder: AccountHolderAng;
    authorities: any[];
    isSaving: boolean;

    mainaccounts: MainAccountAng[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private accountHolderService: AccountHolderAngService,
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
        if (this.accountHolder.id !== undefined) {
            this.accountHolderService.update(this.accountHolder)
                .subscribe((res: AccountHolderAng) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.accountHolderService.create(this.accountHolder)
                .subscribe((res: AccountHolderAng) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: AccountHolderAng) {
        this.eventManager.broadcast({ name: 'accountHolderListModification', content: 'OK'});
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
    selector: 'jhi-account-holder-ang-popup',
    template: ''
})
export class AccountHolderAngPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private accountHolderPopupService: AccountHolderAngPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.accountHolderPopupService
                    .open(AccountHolderAngDialogComponent, params['id']);
            } else {
                this.modalRef = this.accountHolderPopupService
                    .open(AccountHolderAngDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
