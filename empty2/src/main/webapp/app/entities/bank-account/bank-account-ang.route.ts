import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BankAccountAngComponent } from './bank-account-ang.component';
import { BankAccountAngDetailComponent } from './bank-account-ang-detail.component';
import { BankAccountAngPopupComponent } from './bank-account-ang-dialog.component';
import { BankAccountAngDeletePopupComponent } from './bank-account-ang-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class BankAccountAngResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const bankAccountRoute: Routes = [
  {
    path: 'bank-account-ang',
    component: BankAccountAngComponent,
    resolve: {
      'pagingParams': BankAccountAngResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'BankAccounts'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'bank-account-ang/:id',
    component: BankAccountAngDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'BankAccounts'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bankAccountPopupRoute: Routes = [
  {
    path: 'bank-account-ang-new',
    component: BankAccountAngPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'BankAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'bank-account-ang/:id/edit',
    component: BankAccountAngPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'BankAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'bank-account-ang/:id/delete',
    component: BankAccountAngDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'BankAccounts'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
