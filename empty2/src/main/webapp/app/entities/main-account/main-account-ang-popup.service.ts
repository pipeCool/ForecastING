import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MainAccountAng } from './main-account-ang.model';
import { MainAccountAngService } from './main-account-ang.service';
@Injectable()
export class MainAccountAngPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private mainAccountService: MainAccountAngService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.mainAccountService.find(id).subscribe((mainAccount) => {
                this.mainAccountModalRef(component, mainAccount);
            });
        } else {
            return this.mainAccountModalRef(component, new MainAccountAng());
        }
    }

    mainAccountModalRef(component: Component, mainAccount: MainAccountAng): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mainAccount = mainAccount;
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
