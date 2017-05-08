import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BankAccountAng } from './bank-account-ang.model';
import { BankAccountAngService } from './bank-account-ang.service';
@Injectable()
export class BankAccountAngPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bankAccountService: BankAccountAngService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bankAccountService.find(id).subscribe((bankAccount) => {
                bankAccount.fixedAccessDate = this.datePipe
                    .transform(bankAccount.fixedAccessDate, 'yyyy-MM-ddThh:mm');
                this.bankAccountModalRef(component, bankAccount);
            });
        } else {
            return this.bankAccountModalRef(component, new BankAccountAng());
        }
    }

    bankAccountModalRef(component: Component, bankAccount: BankAccountAng): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bankAccount = bankAccount;
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
