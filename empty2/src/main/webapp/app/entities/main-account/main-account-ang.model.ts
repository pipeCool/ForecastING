import { BankAccountAng } from '../bank-account';
import { AccountHolderAng } from '../account-holder';
export class MainAccountAng {
    constructor(
        public id?: number,
        public accountName?: string,
        public kto?: BankAccountAng,
        public holder?: AccountHolderAng,
    ) {
    }
}
