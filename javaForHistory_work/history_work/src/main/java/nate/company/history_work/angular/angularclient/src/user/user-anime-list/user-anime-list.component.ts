import { Component , Input, OnInit , AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Typewriter from 'typewriter-effect/dist/core';

import { User } from '../user';
//movie component
import { AnimeFullInformations, Anime, watchedAnime,
                                   watchedAnimeStatus, } from '../../anime/anime-models/anime-models';
import { AnimeCardComponent } from '../../anime/anime-card/anime-card.component';
import { DefaultUserComponent} from '../../default_user_menu/default-user.component';
//service
import { UserService } from '../user_service/user-service.service';
import {ConnectionServiceService } from '../../connection/connection-service.service';
import { AnimeServiceService } from '../../anime/anime_service/anime-service.service';

@Component({
  selector: 'app-user-anime-list',
  templateUrl: './user-anime-list.component.html',
  styleUrl: './user-anime-list.component.scss'
})
export class UserAnimeListComponent implements OnInit  {
  animesService:AnimeServiceService;
        connectionService:ConnectionServiceService
        userService:UserService;

        currentPage:number;
          numberPerPage:number;

        //static true means the sentences cannot change
          @ViewChild('typewriterElement', { static: true }) typewriterElement!: ElementRef;

        constructor(service:AnimeServiceService, userService:UserService, connectionService:ConnectionServiceService){
          this.animesService = service;
          this.userService = userService;
          //prepare user data to keep the connection
          this.connectionService = connectionService;
          this.prepareUser();
          this.currentPage = 0;
          this.numberPerPage = 3;


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

            this.animesService.retrieveUserAnimes(this.userService.userAccount);
            /*
            .findAllMoviesFromUserList(userService.userAccount.pseudo, userService.userAccount.password).subscribe(
                      list => this.listMovies = list);
              }*/







            console.log("on passe dans le for des animes présents : ");
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
                .typeString("Voici votre liste d'animes : ")
                .pauseFor(1000)
                .start();

          }

         /**
           this method returns some movies by a number of pages*/
          /* deprecated
          MoviesByPage() {
            let moviesPerPage:watchedMovie[] = []
            console.log("on entre dans movies by page")
            for(let i = this.currentPage*this.numberPerPage;i<(this.currentPage+1)*this.numberPerPage;i++){
              moviesPerPage.push(this.movieService.userMoviesList[i])
            }
            return moviesPerPage;
          }*/

        /**

        go to the next page of movies
           */
          increasesPages() {
            if((this.currentPage+1)*this.numberPerPage < this.animesService.userAnimesList.length){
              this.currentPage+=1;
            }
          }

        /**

        go to the previous page of movies
           */
          decreasesPages() {
            if((this.currentPage-1)*this.numberPerPage >= 0){
              this.currentPage-=1;
            }
          }

}
