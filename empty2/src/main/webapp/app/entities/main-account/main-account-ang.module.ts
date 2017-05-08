import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Empty2SharedModule } from '../../shared';
import {
    MainAccountAngService,
    MainAccountAngPopupService,
    MainAccountAngComponent,
    MainAccountAngDetailComponent,
    MainAccountAngDialogComponent,
    MainAccountAngPopupComponent,
    MainAccountAngDeletePopupComponent,
    MainAccountAngDeleteDialogComponent,
    mainAccountRoute,
    mainAccountPopupRoute,
} from './';

const ENTITY_STATES = [
    ...mainAccountRoute,
    ...mainAccountPopupRoute,
];

@NgModule({
    imports: [
        Empty2SharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MainAccountAngComponent,
        MainAccountAngDetailComponent,
        MainAccountAngDialogComponent,
        MainAccountAngDeleteDialogComponent,
        MainAccountAngPopupComponent,
        MainAccountAngDeletePopupComponent,
    ],
    entryComponents: [
        MainAccountAngComponent,
        MainAccountAngDialogComponent,
        MainAccountAngPopupComponent,
        MainAccountAngDeleteDialogComponent,
        MainAccountAngDeletePopupComponent,
    ],
    providers: [
        MainAccountAngService,
        MainAccountAngPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Empty2MainAccountAngModule {}
