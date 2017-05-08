import { MainAccountAng } from '../main-account';
export class AccountHolderAng {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public dob?: any,
        public account?: MainAccountAng,
    ) {
    }
}
