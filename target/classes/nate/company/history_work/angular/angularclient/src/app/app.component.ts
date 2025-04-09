
import { Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { RouterOutlet } from '@angular/router';
import {UserListComponent} from '../user/user_list/user-list.component';

//for data storage

import { UserService } from '../user/user_service/user-service.service';
import { MovieServiceService } from '../movies/movie_service/movie-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {


  title: string;
  isConnected:Boolean;
  userService:UserService;
  movieService:MovieServiceService;


  constructor(userService:UserService, movieService:MovieServiceService) {
    this.isConnected = false;
    this.title = "Historique d'oeuvres artistiques";
    this.userService = userService;
    this.movieService = movieService;
    console.log("on passe par le constructeur de app...");
  }

  /*
  see : https://dev-academy.com/angular-session-storage/
  this method enable to save for a session.
  */
  dataSave(){
    //save user data for long term session
    sessionStorage.setItem('pseudo', ""+this.userService.userAccount.pseudo);
  }
}
