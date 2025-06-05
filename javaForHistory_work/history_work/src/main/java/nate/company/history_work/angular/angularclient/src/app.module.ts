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

import {AnimeMenuUserComponent}from './user/anime-menu-user/anime-menu-user.component';

import { UserService } from './user/user_service/user-service.service';
import {ChatbotService} from './chatbot/chatbot_service/chatbot.service'
import { CommonFunctionalityComponent } from './common-functionality-component/common-functionality-component.component';
import {RouterModule} from '@angular/router';
import { DefaultUserComponent } from './default_user_menu/default-user.component';

import {MovieUserCardComponent } from './user/cards/movie_user_card/movie-user-card.component';
import { ConnectionServiceService } from './connection/connection-service.service';
import { UserEntranceComponent} from './user/user_entrance/user-entrance.component';
import { UserConnectionComponent } from './user/user_connection/user-connection.component';

import {ChatbotComponent } from './chatbot/chatbot.component';
//about movies
import { MovieServiceService } from './movies/movie_service/movie-service.service';
import { NavbarMoviesComponent } from './movies/navbar/navbar-movies.component';
import { AnimeNavbarComponent} from './anime/navbar/anime-navbar/anime-navbar.component'
import {AnimeServiceService } from './anime/anime_service/anime-service.service';
import { MovieListComponent } from './movies/movie_list/movie-list.component';
import { MovieCardComponent } from './movies/movie_card/movie-card.component';
import { AnimeCardComponent } from './anime/anime-card/anime-card.component';
import { AnimeListComponent } from './anime/anime-list/anime-list.component';
import { UserAnimeListComponent } from './user/user-anime-list/user-anime-list.component';
import { MovieSearchComponent } from './movies/movie_search/movie-search.component';
import { LowerCasePipeComponent } from './lower-case-pipe/lower-case-pipe.component';
import { OrderByPipe } from "./order-by/order-by.pipe";
import {AnimeSearchComponent} from './anime/anime-search/anime-search.component';




@NgModule({
   declarations: [
      UserFormComponent,
      AppComponent,
      AnimeMenuUserComponent,
      UserEntranceComponent,
      UserConnectionComponent,
      MovieCardComponent,
      NavbarMoviesComponent,
      AnimeNavbarComponent,
      AnimeCardComponent,
      UserAnimeListComponent,
      MovieListComponent,
      MovieSearchComponent,
      AnimeSearchComponent,
      AnimeListComponent,
      AccueilComponent,
      MovieMenuUserComponent,
      UserMovieListComponent,
      MovieUserCardComponent,
      UserListComponent,
      CommonFunctionalityComponent,
      ChatbotComponent,
      DefaultUserComponent,
      LowerCasePipeComponent,
      OrderByPipe,

    ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      FormsModule,
      RouterModule,

    ],
  providers: [AnimeServiceService, UserService, ConnectionServiceService, MovieServiceService, OrderByPipe],
  bootstrap:[AppComponent],
})
export class AppModule { }
