import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { TransactionAng } from './transaction-ang.model';
import { TransactionAngService } from './transaction-ang.service';
@Injectable()
export class TransactionAngPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private transactionService: TransactionAngService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.transactionService.find(id).subscribe((transaction) => {
                transaction.trxDate = this.datePipe
                    .transform(transaction.trxDate, 'yyyy-MM-ddThh:mm');
                transaction.bookingDate = this.datePipe
                    .transform(transaction.bookingDate, 'yyyy-MM-ddThh:mm');
                this.transactionModalRef(component, transaction);
            });
        } else {
            return this.transactionModalRef(component, new TransactionAng());
        }
    }

    transactionModalRef(component: Component, transaction: TransactionAng): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.transaction = transaction;
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
