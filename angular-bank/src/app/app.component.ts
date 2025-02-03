import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { BankAccount } from './models/bank-account';
import { AccountService } from './services/account.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'angular-bank';
  account!: BankAccount;

  constructor(private accountService: AccountService, private router: Router) {}

  ngOnInit() {}
}
