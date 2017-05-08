import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { BankAccountAng } from './bank-account-ang.model';
import { BankAccountAngPopupService } from './bank-account-ang-popup.service';
import { BankAccountAngService } from './bank-account-ang.service';

@Component({
    selector: 'jhi-bank-account-ang-delete-dialog',
    templateUrl: './bank-account-ang-delete-dialog.component.html'
})
export class BankAccountAngDeleteDialogComponent {

    bankAccount: BankAccountAng;

    constructor(
        private bankAccountService: BankAccountAngService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bankAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bankAccountListModification',
                content: 'Deleted an bankAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bank-account-ang-delete-popup',
    template: ''
})
export class BankAccountAngDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bankAccountPopupService: BankAccountAngPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bankAccountPopupService
                .open(BankAccountAngDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
