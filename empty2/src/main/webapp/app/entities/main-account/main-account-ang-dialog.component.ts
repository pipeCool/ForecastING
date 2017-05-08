import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { MainAccountAng } from './main-account-ang.model';
import { MainAccountAngPopupService } from './main-account-ang-popup.service';
import { MainAccountAngService } from './main-account-ang.service';

@Component({
    selector: 'jhi-main-account-ang-dialog',
    templateUrl: './main-account-ang-dialog.component.html'
})
export class MainAccountAngDialogComponent implements OnInit {

    mainAccount: MainAccountAng;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private mainAccountService: MainAccountAngService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mainAccount.id !== undefined) {
            this.mainAccountService.update(this.mainAccount)
                .subscribe((res: MainAccountAng) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.mainAccountService.create(this.mainAccount)
                .subscribe((res: MainAccountAng) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: MainAccountAng) {
        this.eventManager.broadcast({ name: 'mainAccountListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-main-account-ang-popup',
    template: ''
})
export class MainAccountAngPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mainAccountPopupService: MainAccountAngPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.mainAccountPopupService
                    .open(MainAccountAngDialogComponent, params['id']);
            } else {
                this.modalRef = this.mainAccountPopupService
                    .open(MainAccountAngDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
