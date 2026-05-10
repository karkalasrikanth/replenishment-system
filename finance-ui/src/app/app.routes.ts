import { Routes } from '@angular/router';
import {Dashboard} from './dashboard/dashboard';
import { MsalGuard } from '@azure/msal-angular'

export const routes: Routes = [
  {
    path: '', component: Dashboard, canActivate: [MsalGuard]
  }
];
