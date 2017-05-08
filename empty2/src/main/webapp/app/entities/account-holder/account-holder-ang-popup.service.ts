import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AccountHolderAng } from './account-holder-ang.model';
import { AccountHolderAngService } from './account-holder-ang.service';
@Injectable()
export class AccountHolderAngPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private accountHolderService: AccountHolderAngService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.accountHolderService.find(id).subscribe((accountHolder) => {
                accountHolder.dob = this.datePipe
                    .transform(accountHolder.dob, 'yyyy-MM-ddThh:mm');
                this.accountHolderModalRef(component, accountHolder);
            });
        } else {
            return this.accountHolderModalRef(component, new AccountHolderAng());
        }
    }

    accountHolderModalRef(component: Component, accountHolder: AccountHolderAng): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.accountHolder = accountHolder;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
