import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { MainAccountAngComponent } from './main-account-ang.component';
import { MainAccountAngDetailComponent } from './main-account-ang-detail.component';
import { MainAccountAngPopupComponent } from './main-account-ang-dialog.component';
import { MainAccountAngDeletePopupComponent } from './main-account-ang-delete-dialog.component';

import { Principal } from '../../shared';

export const mainAccountRoute: Routes = [
  {
    path: 'main-account-ang',
    component: MainAccountAngComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MainAccounts'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'main-account-ang/:id',
    component: MainAccountAngDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MainAccounts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const mainAccountPopupRoute: Routes = [
  {
    path: 'main-account-ang-new',
    component: MainAccountAngPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MainAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'main-account-ang/:id/edit',
    component: MainAccountAngPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MainAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'main-account-ang/:id/delete',
    component: MainAccountAngDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'MainAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
