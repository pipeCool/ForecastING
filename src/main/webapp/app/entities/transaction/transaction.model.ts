
const enum TransactionStatus {
    'FIXED',
    'PENDING',
    'PREDICTED'

};
import { Location } from '../location';
import { BankAccount } from '../bank-account';
export class Transaction {
    constructor(
        public id?: number,
        public date?: any,
        public amount?: number,
        public status?: TransactionStatus,
        public location?: Location,
        public bankaccount?: BankAccount,
    ) {
    }
}
