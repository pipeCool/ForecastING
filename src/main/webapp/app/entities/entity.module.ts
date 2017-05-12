import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Interhyp4HackathonBankAccountModule } from './bank-account/bank-account.module';
import { Interhyp4HackathonLabelModule } from './label/label.module';
import { Interhyp4HackathonOperationModule } from './operation/operation.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Interhyp4HackathonBankAccountModule,
        Interhyp4HackathonLabelModule,
        Interhyp4HackathonOperationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Interhyp4HackathonEntityModule {}
