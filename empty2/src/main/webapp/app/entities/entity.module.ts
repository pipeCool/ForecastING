import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Empty2MainAccountAngModule } from './main-account/main-account-ang.module';
import { Empty2AccountHolderAngModule } from './account-holder/account-holder-ang.module';
import { Empty2BankAccountAngModule } from './bank-account/bank-account-ang.module';
import { Empty2TransactionAngModule } from './transaction/transaction-ang.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Empty2MainAccountAngModule,
        Empty2AccountHolderAngModule,
        Empty2BankAccountAngModule,
        Empty2TransactionAngModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Empty2EntityModule {}
