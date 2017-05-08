
const enum Currency {
    'EURO',
    'DOLLAR'

};
import { BankAccountAng } from '../bank-account';
export class TransactionAng {
    constructor(
        public id?: number,
        public trxDate?: any,
        public bookingDate?: any,
        public amount?: number,
        public originalAmount?: number,
        public originalCurrency?: Currency,
        public accountName?: string,
        public account?: BankAccountAng,
    ) {
    }
}
