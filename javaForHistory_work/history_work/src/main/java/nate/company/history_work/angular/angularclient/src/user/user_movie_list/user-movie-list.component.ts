import { Component , Input, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { User } from '../user';
import {MovieUserCardComponent } from '../cards/movie_user_card/movie-user-card.component';
//movie component
import { MovieShortInformations } from '../../movies/movie_models/movie_models';
import { MovieCardComponent } from '../../movies/movie_card/movie-card.component';
import { MovieFullInformations } from '../../movies/movie_models/movie_models';


//service
import { UserService } from '../user_service/user-service.service';
import {ConnectionServiceService } from '../../connection/connection-service.service';
import { MovieServiceService } from '../../movies/movie_service/movie-service.service';


@Component({
  selector: 'app-user-movie-list',
  templateUrl: './user-movie-list.component.html',
  styleUrl: './user-movie-list.component.scss'
})
export class UserMovieListComponent implements OnInit {
      movieService:MovieServiceService;
      connectionService:ConnectionServiceService
      userService:UserService;
      constructor(service:MovieServiceService, userService:UserService, connectionService:ConnectionServiceService){
        this.movieService = service;
        this.userService = userService;
        //load user's movies
        if(this.movieService.userMoviesList.length == 0){
                    this.movieService.retrieveUserMovies(this.userService.userAccount);
        }
        //prepare user data to keep the connection
        this.connectionService = connectionService;
        this.userService.prepareConnection(this.connectionService);
        console.log("les données ont bien été récupérées, le user a pour pseudo :"+this.userService.userAccount.pseudo);
      }
      ngOnInit() {}
}
