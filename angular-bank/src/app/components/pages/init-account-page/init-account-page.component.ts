import { Component } from '@angular/core';
import { AccountService } from '../../../services/account.service';
import { Router } from '@angular/router';
import { SelectAccountComponent } from '../../shared/select-account/select-account.component';

@Component({
  selector: 'app-init-account-page',
  imports: [SelectAccountComponent],
  templateUrl: './init-account-page.component.html',
  styleUrl: './init-account-page.component.css',
})
export class InitAccountPageComponent {
  constructor(private accountService: AccountService, private router: Router) {}

  initAccount() {
    this.accountService.initAccount().subscribe({
      next: (data) => {
        console.log('initAccount');
        console.log(data);
        this.router.navigate([`account/${data.id}`]);
      },
      error: (err) => {
        alert(err.error);
      },
    });
  }
}
