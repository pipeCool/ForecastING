import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Empty2SharedModule } from '../../shared';
import {
    TransactionAngService,
    TransactionAngPopupService,
    TransactionAngComponent,
    TransactionAngDetailComponent,
    TransactionAngDialogComponent,
    TransactionAngPopupComponent,
    TransactionAngDeletePopupComponent,
    TransactionAngDeleteDialogComponent,
    transactionRoute,
    transactionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...transactionRoute,
    ...transactionPopupRoute,
];

@NgModule({
    imports: [
        Empty2SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TransactionAngComponent,
        TransactionAngDetailComponent,
        TransactionAngDialogComponent,
        TransactionAngDeleteDialogComponent,
        TransactionAngPopupComponent,
        TransactionAngDeletePopupComponent,
    ],
    entryComponents: [
        TransactionAngComponent,
        TransactionAngDialogComponent,
        TransactionAngPopupComponent,
        TransactionAngDeleteDialogComponent,
        TransactionAngDeletePopupComponent,
    ],
    providers: [
        TransactionAngService,
        TransactionAngPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Empty2TransactionAngModule {}
