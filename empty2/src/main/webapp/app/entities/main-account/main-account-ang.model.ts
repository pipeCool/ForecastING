import { BankAccount } from '../bank-account';
import { AccountHolder } from '../account-holder';
export class MainAccountAng {
    constructor(
        public id?: number,
        public accountName?: string,
        public kto?: BankAccount,
        public holder?: AccountHolder,
    ) {
    }
}
