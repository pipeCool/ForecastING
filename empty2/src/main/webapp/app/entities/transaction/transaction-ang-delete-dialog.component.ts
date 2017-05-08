import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { TransactionAng } from './transaction-ang.model';
import { TransactionAngPopupService } from './transaction-ang-popup.service';
import { TransactionAngService } from './transaction-ang.service';

@Component({
    selector: 'jhi-transaction-ang-delete-dialog',
    templateUrl: './transaction-ang-delete-dialog.component.html'
})
export class TransactionAngDeleteDialogComponent {

    transaction: TransactionAng;

    constructor(
        private transactionService: TransactionAngService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.transactionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'transactionListModification',
                content: 'Deleted an transaction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transaction-ang-delete-popup',
    template: ''
})
export class TransactionAngDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private transactionPopupService: TransactionAngPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.transactionPopupService
                .open(TransactionAngDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
