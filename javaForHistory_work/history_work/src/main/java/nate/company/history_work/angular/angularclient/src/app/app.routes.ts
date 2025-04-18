import { Routes } from '@angular/router';
import { UserListComponent } from '../user/user_list/user-list.component';
import { UserFormComponent } from '../user/user_form/user-form.component';
import { UserService } from '../user/user_service/user-service.service';
import {AppComponent } from './app.component';

export const routes: Routes = [
  { path: 'entry', component: AppComponent },
    { path: 'users', component: UserListComponent },
    { path: 'adduser', component: UserFormComponent },
  ];
