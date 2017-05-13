import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Interhyp4HackathonLabelModule } from './label/label.module';
import { Interhyp4HackathonOperationModule } from './operation/operation.module';
import { Interhyp4HackathonBankAccountModule } from './bank-account/bank-account.module';
import { Interhyp4HackathonTransactionModule } from './transaction/transaction.module';
import { Interhyp4HackathonLocationModule } from './location/location.module';
import { Interhyp4HackathonCalendarModule } from '../calendar/calendar.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Interhyp4HackathonLabelModule,
        Interhyp4HackathonOperationModule,
        Interhyp4HackathonBankAccountModule,
        Interhyp4HackathonTransactionModule,
        Interhyp4HackathonLocationModule,
        Interhyp4HackathonCalendarModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Interhyp4HackathonEntityModule {}
