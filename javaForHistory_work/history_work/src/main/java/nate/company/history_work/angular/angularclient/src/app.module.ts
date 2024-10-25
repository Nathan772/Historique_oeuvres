/*
généré manuellement + baeldung
+ moi
*/

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app/app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { UserListComponent } from './user/user_list/user-list.component';
import { UserFormComponent } from './user/user_form/user-form.component';
import { UserService } from './user/user_service/user-service.service';
import {RouterModule} from '@angular/router';


import { ConnectionServiceService } from './connection/connection-service.service';
import { UserEntranceComponent} from './user/user_entrance/user-entrance.component';
import { UserConnectionComponent } from './user/user_connection/user-connection.component';


//about movies
import { MovieServiceService } from './movies/movie_service/movie-service.service';
import { NavbarMoviesComponent } from './movies/navbar/navbar-movies.component';
import { MovieListComponent } from './movies/movie_list/movie-list.component';
import { MovieCardComponent } from './movies/movie_card/movie-card.component';
import { MovieSearchComponent } from './movies/movie_search/movie-search.component';





@NgModule({
   declarations: [
      AppComponent,
      UserListComponent,
      UserFormComponent,
      UserEntranceComponent,
      UserConnectionComponent,
      MovieCardComponent,
      NavbarMoviesComponent,
      MovieListComponent,
      MovieSearchComponent,
    ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule,
      RouterModule
    ],
  providers: [UserService, ConnectionServiceService, MovieServiceService],
  bootstrap:[AppComponent],
})
export class AppModule { }
