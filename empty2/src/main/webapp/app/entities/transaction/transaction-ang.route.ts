import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TransactionAngComponent } from './transaction-ang.component';
import { TransactionAngDetailComponent } from './transaction-ang-detail.component';
import { TransactionAngPopupComponent } from './transaction-ang-dialog.component';
import { TransactionAngDeletePopupComponent } from './transaction-ang-delete-dialog.component';

import { Principal } from '../../shared';

export const transactionRoute: Routes = [
  {
    path: 'transaction-ang',
    component: TransactionAngComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'transaction-ang/:id',
    component: TransactionAngDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionPopupRoute: Routes = [
  {
    path: 'transaction-ang-new',
    component: TransactionAngPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'transaction-ang/:id/edit',
    component: TransactionAngPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'transaction-ang/:id/delete',
    component: TransactionAngDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
