import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MovieServiceService } from '../movie_service/movie-service.service';
import { NavbarMoviesComponent } from '../navbar/navbar-movies.component';
import { MovieListComponent } from '../movie_list/movie-list.component';
import { MovieCardComponent } from '../movie_card/movie-card.component';
import { MovieShortInformations, SearchResponse, MovieFullInformations, Rating} from '../movie_models/movie_models';
import {UserService } from '../../user/user_service/user-service.service';
import {ConnectionServiceService } from '../../connection/connection-service.service';
import { Movie} from '../movie_models/movie_models';

@Component({
  selector: 'app-movie-search',
  templateUrl: './movie-search.component.html',
  styleUrl: './movie-search.component.scss'
})
export class MovieSearchComponent {

   /* cette variable va stocker les films
    retrouvés suite à la recherche */
    listMoviesTransfer: MovieShortInformations[] = [];
    //listMovieTransfer:Movie[] = [];
    /* cette instance est nécessaire pour
    pouvoir utiliser les fonctions de type film-service.
    Mais attention ce n'est pas suffisant,
    il faut aussi faire les imports
     des https client, manuellement, dans
     appModule ainsi que dans
     ngmodule de appmodule, sinon
     il y aura une erreur lors des appels
     https et dans votre console
     Il faut aussi initialiser le service dans le
     constructeur (voir tout en bas) sinon il y aura des
     pbs*/
    movieService: MovieServiceService;
    userService:UserService;
    connectionService:ConnectionServiceService;
    hideSearchResult:boolean = false;
    statusSearch:string = "cacher?";
     /*on va utiliser cet émeteur pour émettre un tableau
    qui correspond à la liste de films
    trouvée par l'utilisateur lors de sa recherche.
    Il faut savoir que
    L'émetteur est possédé par celui qui émet l'information
    c'est à dire celui qui sait qu'il y a eu un
    événement et qui va avertir l'autre composant
    en lui donnant ces infos*/
    @Output()
    arrayFound = new EventEmitter<MovieShortInformations[]>();

    startSearch(chaine: string) {
      //in this state search is forbidden
      if(this.hideSearchResult)
      {
        return;
      }
    //search is allowed
      console.log('le user a recherché ' + chaine);
      /* Il faudra appeler une fonction du service movie-service
       this.search et l'utiliser pour faire la recherche dans la bdd*/

      this.movieService.getMovieShort(chaine).subscribe((data) => {
        /* on s'assure que le film a bien été trouvé pour
        afficher la chaine */
        /* on donne à "listFilms" la liste des
        films associé au nom recherché par le user.*/
        if (data != null && data.length > 0) {
          console.log("la liste est non-vide");
          //Only accept movies and no other kind
          //this.listMoviesTransfer= data.filter(movie=>movie.Type === "movie");
          //enable everything ()
          this.listMoviesTransfer= data.filter(movie=>movie.Type === "movie");

          //Deprecated
          //for(var movie of this.listMovies){
            /*
            id:string;
              title: string;
              yearOfRelease: string;
              director: string;
              imdbID: string;
              poster:string;
              */
             //let movie1 = new Movie(movie.Title, movie.Year, movie.Director,movie.imdbID, movie.Poster);
            //this.listMovieTransfer.push(movie1);
          //}
        }
      });

      /* on va émettre mais seulement si la liste de
      films est non-vide*/
        this.arrayFound.emit(this.listMoviesTransfer);
    }

         /* l'initialisation
    du service dans le constructeur
    est indispensable*/
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
  changeHideStatus(){
    //reverse the status
    this.hideSearchResult = !this.hideSearchResult;
    if(this.hideSearchResult === true){
      this.statusSearch = "afficher ?"
    }
  else {
          this.statusSearch = "cacher ?"
        }

  }

    ngOnInit() {
      this.hideSearchResult = false;
    }
}
