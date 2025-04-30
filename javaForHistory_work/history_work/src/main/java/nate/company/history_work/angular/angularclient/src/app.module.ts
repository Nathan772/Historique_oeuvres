/*
généré manuellement + baeldung
+ moi
*/

import { BrowserModule } from '@angular/platform-browser';
//import { TypingAnimationModule } from "angular-typing-animation";
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app/app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { UserListComponent } from './user/user_list/user-list.component';
import { UserFormComponent } from './user/user_form/user-form.component';
import { UserMovieListComponent } from './user/user_movie_list/user-movie-list.component';
import { AccueilComponent } from './accueil/accueil.component';
import { MovieMenuUserComponent } from './user/movie_menu_user/movie-menu-user.component';
import { UserService } from './user/user_service/user-service.service';
import { CommonFunctionalityComponent } from './common-functionality-component/common-functionality-component.component';
import {RouterModule} from '@angular/router';

import {MovieUserCardComponent } from './user/cards/movie_user_card/movie-user-card.component';
import { ConnectionServiceService } from './connection/connection-service.service';
import { UserEntranceComponent} from './user/user_entrance/user-entrance.component';
import { UserConnectionComponent } from './user/user_connection/user-connection.component';

import {ChatbotComponent } from './chatbot/chatbot.component';
//about movies
import { MovieServiceService } from './movies/movie_service/movie-service.service';
import { NavbarMoviesComponent } from './movies/navbar/navbar-movies.component';
import { MovieListComponent } from './movies/movie_list/movie-list.component';
import { MovieCardComponent } from './movies/movie_card/movie-card.component';
import { MovieSearchComponent } from './movies/movie_search/movie-search.component';





@NgModule({
   declarations: [
      UserFormComponent,
      AppComponent,
      UserEntranceComponent,
      UserConnectionComponent,
      MovieCardComponent,
      NavbarMoviesComponent,
      MovieListComponent,
      MovieSearchComponent,
      AccueilComponent,
      MovieMenuUserComponent,
      UserMovieListComponent,
      MovieUserCardComponent,
      UserListComponent,
      CommonFunctionalityComponent,
      ChatbotComponent,

    ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule,
      RouterModule,

    ],
  providers: [UserService, ConnectionServiceService, MovieServiceService],
  bootstrap:[AppComponent],
})
export class AppModule { }
