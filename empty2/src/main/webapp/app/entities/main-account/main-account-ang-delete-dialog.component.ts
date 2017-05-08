import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { MainAccountAng } from './main-account-ang.model';
import { MainAccountAngPopupService } from './main-account-ang-popup.service';
import { MainAccountAngService } from './main-account-ang.service';

@Component({
    selector: 'jhi-main-account-ang-delete-dialog',
    templateUrl: './main-account-ang-delete-dialog.component.html'
})
export class MainAccountAngDeleteDialogComponent {

    mainAccount: MainAccountAng;

    constructor(
        private mainAccountService: MainAccountAngService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mainAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mainAccountListModification',
                content: 'Deleted an mainAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-main-account-ang-delete-popup',
    template: ''
})
export class MainAccountAngDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mainAccountPopupService: MainAccountAngPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.mainAccountPopupService
                .open(MainAccountAngDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
