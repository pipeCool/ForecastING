import { Transaction } from '../transaction';
import { User } from '../../shared';
export class BankAccount {
    constructor(
        public id?: number,
        public iban?: string,
        public bank?: string,
        public currentAmount?: number,
        public transaction?: Transaction,
        public user?: User,
    ) {
    }
}
