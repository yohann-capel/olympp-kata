import { Component } from '@angular/core';
import { BankAccount } from '../../../models/bank-account';
import { AccountService } from '../../../services/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-select-account',
  imports: [],
  templateUrl: './select-account.component.html',
  styleUrl: './select-account.component.css',
})
export class SelectAccountComponent {
  public bankAccounts: BankAccount[] = [];

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit() {
    this.accountService.getAll().subscribe((data) => {
      this.bankAccounts = data;
    });
  }

  goTo(accountId: number) {
    this.router.navigate([`account/${accountId}`]);
  }
}
