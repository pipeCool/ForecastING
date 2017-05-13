import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TransactionMySuffixComponent } from './transaction-my-suffix.component';
import { TransactionMySuffixDetailComponent } from './transaction-my-suffix-detail.component';
import { TransactionMySuffixPopupComponent } from './transaction-my-suffix-dialog.component';
import { TransactionMySuffixDeletePopupComponent } from './transaction-my-suffix-delete-dialog.component';

import { Principal } from '../../shared';

export const transactionRoute: Routes = [
    {
        path: 'transaction-my-suffix',
        component: TransactionMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'interhyp4HackathonApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'transaction-my-suffix/:id',
        component: TransactionMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'interhyp4HackathonApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transactionPopupRoute: Routes = [
    {
        path: 'transaction-my-suffix-new',
        component: TransactionMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'interhyp4HackathonApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transaction-my-suffix/:id/edit',
        component: TransactionMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'interhyp4HackathonApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'transaction-my-suffix/:id/delete',
        component: TransactionMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'interhyp4HackathonApp.transaction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
