import { Statement } from './statement';

export interface BankAccount {
  id: number;
  balance: number;
  statements: Statement[];
}
