import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Interhyp4HackathonSharedModule } from '../../shared';
import {
    TransactionMySuffixService,
    TransactionMySuffixPopupService,
    TransactionMySuffixComponent,
    TransactionMySuffixDetailComponent,
    TransactionMySuffixDialogComponent,
    TransactionMySuffixPopupComponent,
    TransactionMySuffixDeletePopupComponent,
    TransactionMySuffixDeleteDialogComponent,
    transactionRoute,
    transactionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...transactionRoute,
    ...transactionPopupRoute,
];

@NgModule({
    imports: [
        Interhyp4HackathonSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TransactionMySuffixComponent,
        TransactionMySuffixDetailComponent,
        TransactionMySuffixDialogComponent,
        TransactionMySuffixDeleteDialogComponent,
        TransactionMySuffixPopupComponent,
        TransactionMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        TransactionMySuffixComponent,
        TransactionMySuffixDialogComponent,
        TransactionMySuffixPopupComponent,
        TransactionMySuffixDeleteDialogComponent,
        TransactionMySuffixDeletePopupComponent,
    ],
    providers: [
        TransactionMySuffixService,
        TransactionMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Interhyp4HackathonTransactionMySuffixModule {}
