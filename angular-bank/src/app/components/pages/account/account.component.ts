import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../../../services/account.service';
import { BankAccount } from '../../../models/bank-account';
import { StatementsDisplayerComponent } from '../../shared/statements-displayer/statements-displayer.component';
import { AmountData } from '../../../models/amount-data';
import { FormsModule } from '@angular/forms';
import { SelectAccountComponent } from '../../shared/select-account/select-account.component';
import { PrintService } from '../../../services/print.service';

@Component({
  selector: 'app-account',
  imports: [StatementsDisplayerComponent, FormsModule, SelectAccountComponent],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css',
})
export class AccountComponent {
  public bankAccount: BankAccount | null = null;
  public inputValue: number = 0;

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
    private printService: PrintService,
    private router: Router
  ) {}

  ngOnInit() {
    const accountId = this.route.snapshot.paramMap.get('id');

    if (accountId !== null) {
      this.accountService.getById(+accountId).subscribe({
        next: (data) => {
          console.log('Accounts work!');
          console.log(data);
          this.bankAccount = data;
        },
        error: (err) => {
          alert(err.error);
          this.router.navigate(['']);
        },
      });
    }
  }

  deposit(amountData: AmountData) {
    console.log('deposit');
    this.accountService.deposit(amountData).subscribe({
      next: (data: BankAccount) => {
        this.bankAccount = data;
      },
      error: (err) => {
        alert(err.error);
      },
    });
  }

  withdraw(amountData: AmountData) {
    console.log('withdraw');
    this.accountService.withdraw(amountData).subscribe({
      next: (data: BankAccount) => {
        this.bankAccount = data;
      },
      error: (err) => {
        alert(err.error);
      },
    });
  }

  print(accountId: number) {
    this.printService.printAccountData(accountId).subscribe((data) => {
      this.downloadFile(data);
    });
  }

  downloadFile(content: string) {
    const blob = new Blob([content], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'accountData.txt';
    a.click();
    window.URL.revokeObjectURL(url);
  }
}
