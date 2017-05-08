import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AccountHolderAngComponent } from './account-holder-ang.component';
import { AccountHolderAngDetailComponent } from './account-holder-ang-detail.component';
import { AccountHolderAngPopupComponent } from './account-holder-ang-dialog.component';
import { AccountHolderAngDeletePopupComponent } from './account-holder-ang-delete-dialog.component';

import { Principal } from '../../shared';

export const accountHolderRoute: Routes = [
  {
    path: 'account-holder-ang',
    component: AccountHolderAngComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AccountHolders'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'account-holder-ang/:id',
    component: AccountHolderAngDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AccountHolders'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountHolderPopupRoute: Routes = [
  {
    path: 'account-holder-ang-new',
    component: AccountHolderAngPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AccountHolders'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'account-holder-ang/:id/edit',
    component: AccountHolderAngPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AccountHolders'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'account-holder-ang/:id/delete',
    component: AccountHolderAngDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AccountHolders'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
