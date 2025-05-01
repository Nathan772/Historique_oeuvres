import { Component , Input, OnInit , AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Typewriter from 'typewriter-effect/dist/core';

import { User } from '../user';
import {MovieUserCardComponent } from '../cards/movie_user_card/movie-user-card.component';
//movie component
import { MovieShortInformations } from '../../movies/movie_models/movie_models';
import { MovieCardComponent } from '../../movies/movie_card/movie-card.component';
import { MovieFullInformations } from '../../movies/movie_models/movie_models';
import { DefaultUserComponent} from '../../default_user_menu/default-user.component';


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

      //static true means the sentences cannot change
        @ViewChild('typewriterElement', { static: true }) typewriterElement!: ElementRef;

      constructor(service:MovieServiceService, userService:UserService, connectionService:ConnectionServiceService){
        this.movieService = service;
        this.userService = userService;
        //prepare user data to keep the connection
        this.connectionService = connectionService;
        this.prepareUser();


      }

      /*
      this method prepare the user data in
      an asynchronous manner.
      It's necessary to retrieve data base information in the
      proper way cause data don't come in a serialized manner but
      in parallel tasks.
      Constructor cannto by async
      */
      async prepareUser(){
        await this.userService.prepareConnection(this.connectionService);
        //console.log("les données ont bien été récupérées, le user a pour pseudo :"+this.userService.userAccount.pseudo);
        //load user's movies

          this.movieService.retrieveUserMovies(this.userService.userAccount);
          /*
          .findAllMoviesFromUserList(userService.userAccount.pseudo, userService.userAccount.password).subscribe(
                    list => this.listMovies = list);
            }*/







          console.log("on passe dans le for des films présents : ");
          /*for(let i=0;i<this.movieService.userMoviesList.length;i++){
            console.log("les films présents après la récupération  : "+this.movieService.userMoviesList[i].movie.imdbID);
          }*/

    }



      ngOnInit() {}

      //necessary for progressive writing
      ngAfterViewInit(): void {
            const typewriter = new Typewriter(this.typewriterElement.nativeElement, {
              loop: true,
              delay: 75,
            });
            typewriter.erasable = true

            typewriter
               .pauseFor(500)
              .typeString("Préparez vous... ")
              .pauseFor(1000)
              .deleteAll()
              .typeString("Voici votre liste de films : ")
              .pauseFor(1000)
              .start();

        }
}
