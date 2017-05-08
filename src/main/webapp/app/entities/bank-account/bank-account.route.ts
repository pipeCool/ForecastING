import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BankAccountComponent } from './bank-account.component';
import { BankAccountDetailComponent } from './bank-account-detail.component';
import { BankAccountPopupComponent } from './bank-account-dialog.component';
import { BankAccountDeletePopupComponent } from './bank-account-delete-dialog.component';

import { Principal } from '../../shared';

export const bankAccountRoute: Routes = [
    {
        path: 'bank-account',
        component: BankAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationNg2App.bankAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bank-account/:id',
        component: BankAccountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationNg2App.bankAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bankAccountPopupRoute: Routes = [
    {
        path: 'bank-account-new',
        component: BankAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationNg2App.bankAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bank-account/:id/edit',
        component: BankAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationNg2App.bankAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bank-account/:id/delete',
        component: BankAccountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterSampleApplicationNg2App.bankAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
