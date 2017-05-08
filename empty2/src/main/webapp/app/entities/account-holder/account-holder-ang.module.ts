import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Empty2SharedModule } from '../../shared';
import {
    AccountHolderAngService,
    AccountHolderAngPopupService,
    AccountHolderAngComponent,
    AccountHolderAngDetailComponent,
    AccountHolderAngDialogComponent,
    AccountHolderAngPopupComponent,
    AccountHolderAngDeletePopupComponent,
    AccountHolderAngDeleteDialogComponent,
    accountHolderRoute,
    accountHolderPopupRoute,
} from './';

const ENTITY_STATES = [
    ...accountHolderRoute,
    ...accountHolderPopupRoute,
];

@NgModule({
    imports: [
        Empty2SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AccountHolderAngComponent,
        AccountHolderAngDetailComponent,
        AccountHolderAngDialogComponent,
        AccountHolderAngDeleteDialogComponent,
        AccountHolderAngPopupComponent,
        AccountHolderAngDeletePopupComponent,
    ],
    entryComponents: [
        AccountHolderAngComponent,
        AccountHolderAngDialogComponent,
        AccountHolderAngPopupComponent,
        AccountHolderAngDeleteDialogComponent,
        AccountHolderAngDeletePopupComponent,
    ],
    providers: [
        AccountHolderAngService,
        AccountHolderAngPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Empty2AccountHolderAngModule {}
