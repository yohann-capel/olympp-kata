import { Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { AccountComponent } from './components/pages/account/account.component';
import { InitAccountPageComponent } from './components/pages/init-account-page/init-account-page.component';

export const routes: Routes = [
  { path: 'account/:id', component: AccountComponent },
  { path: '', component: InitAccountPageComponent },
  { path: '**', component: InitAccountPageComponent },
];
