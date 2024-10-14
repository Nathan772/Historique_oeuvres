/*import { Component } from '@angular/core';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'angularclient';
}
deprecated
*/

import { Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { RouterOutlet } from '@angular/router';
import {UserListComponent} from '../user/user_list/user-list.component'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {


  title: string;


  constructor() {
    this.title = 'Spring Boot feat. Angular Application';
  }
}
