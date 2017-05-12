
const enum TransactionStatus {
    'FIXED',
    'PENDING',
    'PREDICTED'

};
import { LocationMySuffix } from '../location';
import { BankAccount } from '../bank-account';
export class TransactionMySuffix {
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
