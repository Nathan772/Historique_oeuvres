import { Component, Input, OnInit } from '@angular/core';
import { MovieFullInformations, MovieShortInformations } from '../movie_models/movie_models';
import { MovieServiceService } from '../movie_service/movie-service.service';
import { Movie,watchedMovieStatus} from '../movie_models/movie_models';
import { BrowserModule } from '@angular/platform-browser';
import { UserService } from '../../user/user_service/user-service.service';
//import { CommonFunctionalityComponent } from '../../common-functionality-component/common-functionality.component';
import { CommonFunctionalityComponent } from '../../common-functionality-component/common-functionality-component.component';
@Component({
  selector: 'app-movie-card',
  templateUrl: './movie-card.component.html',
  styleUrls: ['./movie-card.component.css'],
})
export class MovieCardComponent implements OnInit {
  @Input()
  //movie: MovieShortInformations;
  movie: MovieShortInformations;
  watchedMovieStatus = watchedMovieStatus;
  /*
  pour init ce champ, cela ne suffit
  pas il faut aussi l'initialiser au niveau
  du contructeur (voir constructeur)
  */
  movieService: MovieServiceService;
  @Input()
  movieFull: MovieFullInformations;
  fullInfoOn: boolean = false;
  userService:UserService;

  ngOnInit() {}

  /* l'initialisation
    du service dans le constructeur
    est indispensable*/
    constructor(movieService: MovieServiceService, userService:UserService) {
      this.movieService = movieService;
      this.userService = userService;
      this.movieFull = {
        Title: "",
          Year: "",
          Genre: "",
          Director: "",
          Plot:"",
          Awards: "",
          Poster: "",
          Ratings: [],
          imdbID: "",
          Type: "",
         };
       this.movie = {
          Title: "",
           Year: "",
           imdbID: "",
           Poster: "",
           Type: "",
           }

         if(this.movieService.userMoviesList.length == 0){
            this.movieService.retrieveUserMovies(userService.userAccount);
         }

    }

  /* cette fonction donne les infos complètes
   sur un film à la variable movieFullen en se servant d'un service. */
  completeInformations(imdbID: string) {
    this.movieService.getMovieComplete(imdbID).subscribe((data) => {
      /*
      on s'assure que le film a bien été trouvé
      avant de l'affecter à this.movieFull*/
      /* on donne à movieFull
      les infos du film qui nous intéresse*/
      if (data != null) {
        console.log('le film a bien été trouvé');
        this.movieFull = data;
        /* on indique qu'on passe en mode informations complètes*/
        this.fullInfoOn = true;
      }
    });
  }

  /**
   add a movie to user watchList
   */
    addMovieToUserInDataBaseAsWatchLater(movie:MovieFullInformations, movieStatus:watchedMovieStatus){
      //this.userService.
      this.movieService.addMovieToUserInDataBaseAsWatchLater(movie, movieStatus, this.userService.userAccount);
      //reload component to keep consistent page
                 //window.location.reload();
    }

    removeFromWatchListAndDataBase(movie:MovieFullInformations){
        //this.userService.
        this.movieService.removeMovieFromUserInDataBase(movie, this.userService.userAccount);
        //reload component to keep consistent page
                   //window.location.reload();
        //remove movie from movie list
        /*let index = this.movieService.userMoviesList.indexOf(movie);
        if(index!==-1){
          console.log("film retiré de la liste de l'utilisateur : "+movie.imdbID);
          this.movieService.userMoviesList.splice(index,1);
        }*/

        /*for(let i=0;i<this.movieService.userMoviesList.length;i++){
                //console.log("les films présents : "+this.userMoviesList[i].imdbID);
                if(this.movieService.userMoviesList[i].imdbID === movie.imdbID){
                  //console.log("le film est déjà présent : "+this.userMoviesList[i].imdbID);
                  let index = this.movieService.userMoviesList.indexOf(movie);
                  if(index!==-1){
                    console.log("film retiré de la liste de l'utilisateur : "+movie.imdbID);
                    this.movieService.userMoviesList.splice(index,1);
                  }
                  else{
                    console.log("le film qui doit être retiré n'a pas été trouvé");
                  }
                }
              }*/

    }

  /**

  this method enables to prepare a list with all the movies registered by the user in database.

   */
   /*
  retrieveUserMovies(){
    console.log("on récupère les films du user "+this.userService.userAccount.pseudo);
    this.movieService.findAllMoviesFromUserList(this.userService.userAccount.id).subscribe((movies) => {

         on s'assure que le film a bien été trouvé
         avant de l'affecter à this.movieFull
          /* on donne à movieFull
          les infos du film qui nous intéresse
              if (movies != null){
                console.log('les films ont bien été trouvé');
                for(let movie of movies){
                  console.log("on ajoute zzz "+movie.title);
                  console.log("le imdb est : "+movie.imdbID);
                  this.movieService.getMovieComplete(movie.imdbID).subscribe((movieComplete) => {
                    console.log("film trouvé avec l'API : "+movieComplete.imdbID+ " "+movieComplete.Title);
                    this.addToWatchList(movieComplete);
                  });
                }
              }

          }
        );
  }*/


  /* cette fonction va désactiver le mode full
  information*/
  disableFull() {
    this.fullInfoOn = false;
  }


}
/*
ng generate component CommonFunctionalityComponent
*/
