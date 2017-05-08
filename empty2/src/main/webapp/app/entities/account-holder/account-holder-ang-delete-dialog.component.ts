import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AccountHolderAng } from './account-holder-ang.model';
import { AccountHolderAngPopupService } from './account-holder-ang-popup.service';
import { AccountHolderAngService } from './account-holder-ang.service';

@Component({
    selector: 'jhi-account-holder-ang-delete-dialog',
    templateUrl: './account-holder-ang-delete-dialog.component.html'
})
export class AccountHolderAngDeleteDialogComponent {

    accountHolder: AccountHolderAng;

    constructor(
        private accountHolderService: AccountHolderAngService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.accountHolderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'accountHolderListModification',
                content: 'Deleted an accountHolder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-account-holder-ang-delete-popup',
    template: ''
})
export class AccountHolderAngDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private accountHolderPopupService: AccountHolderAngPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.accountHolderPopupService
                .open(AccountHolderAngDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
