
const enum Currency {
    'EURO',
    'DOLLAR'

};

const enum AccountType {
    'GIRO',
    'DAILY',
    'FIXED'

};
import { TransactionAng } from '../transaction';
import { MainAccountAng } from '../main-account';
export class BankAccountAng {
    constructor(
        public id?: number,
        public accountName?: string,
        public fixedAccessDate?: any,
        public accessDelay?: number,
        public currency?: Currency,
        public type?: AccountType,
        public transaction?: TransactionAng,
        public account?: MainAccountAng,
    ) {
    }
}