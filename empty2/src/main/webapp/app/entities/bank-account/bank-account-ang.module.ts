import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Empty2SharedModule } from '../../shared';
import {
    BankAccountAngService,
    BankAccountAngPopupService,
    BankAccountAngComponent,
    BankAccountAngDetailComponent,
    BankAccountAngDialogComponent,
    BankAccountAngPopupComponent,
    BankAccountAngDeletePopupComponent,
    BankAccountAngDeleteDialogComponent,
    bankAccountRoute,
    bankAccountPopupRoute,
    BankAccountAngResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bankAccountRoute,
    ...bankAccountPopupRoute,
];

@NgModule({
    imports: [
        Empty2SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BankAccountAngComponent,
        BankAccountAngDetailComponent,
        BankAccountAngDialogComponent,
        BankAccountAngDeleteDialogComponent,
        BankAccountAngPopupComponent,
        BankAccountAngDeletePopupComponent,
    ],
    entryComponents: [
        BankAccountAngComponent,
        BankAccountAngDialogComponent,
        BankAccountAngPopupComponent,
        BankAccountAngDeleteDialogComponent,
        BankAccountAngDeletePopupComponent,
    ],
    providers: [
        BankAccountAngService,
        BankAccountAngPopupService,
        BankAccountAngResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Empty2BankAccountAngModule {}
