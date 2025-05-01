import { Component, Input, OnInit, viewChild } from '@angular/core';
import { MovieFullInformations, MovieShortInformations, watchedMovieStatus, watchedMovie, Movie } from '../../../movies/movie_models/movie_models';
import { MovieServiceService } from '../../../movies/movie_service/movie-service.service';
import { BrowserModule } from '@angular/platform-browser';
import { UserService } from '../../user_service/user-service.service';
//import { CommonFunctionalityComponent } from '../../common-functionality-component/common-functionality-component.component';
import { CommonFunctionalityComponent } from '../../../common-functionality-component/common-functionality-component.component';
//declare function greet():void;
//declare function showHiddenStatus():void;
import { Router } from '@angular/router';
import { NgForm, FormsModule } from '@angular/forms';
import {ConnectionServiceService } from '../../../connection/connection-service.service';
@Component({
  selector: 'app-movie-user-card',
  templateUrl: './movie-user-card.component.html',
  styleUrls: ['./movie-user-card.component.css'],
})
export class MovieUserCardComponent  extends CommonFunctionalityComponent implements OnInit  {


  // create a signal that monitor the creation of a movie
  //formPauseTime = viewChild<NgForm>('formPauseTime');

  //@Input()
  //movie: MovieShortInformations;
  /*
  pour init ce champ, cela ne suffit
  pas il faut aussi l'initialiser au niveau
  du contructeur (voir constructeur)
  */
  movieService: MovieServiceService;
  @Input()
  movieFull: watchedMovie;

connectionService:ConnectionServiceService;
  movieFromApi:MovieFullInformations;
  fullInfoOn: boolean = false;
  //userService:UserService;
  //necessary to use the enum in the html part
  watchedMovieStatus = watchedMovieStatus;

  pauseTime:string = '00:00';

/*
override
is necessary due to router
path
*/

  override ngOnInit() {

  }

  /* l'initialisation
    du service dans le constructeur
    est indispensable*/
    constructor(movieService: MovieServiceService, public override userService:UserService,
      public override router:Router, connectionService:ConnectionServiceService ) {
        super(userService,router);
      this.movieService = movieService;
      this.userService = userService;
      this.movieFull = {
                                   movie:{id:"",title:"",yearOfRelease:"",director:"",imdbID:"",poster:""},
                                   movieStatus:watchedMovieStatus.WATCHLATER,
                                      time: {
                                                               hours:0,
                                                               minutes:0,
                                                               seconds:0,

                                                               },

                                   };

         if(this.movieService.userMoviesList.length == 0){
            this.movieService.retrieveUserMovies(userService.userAccount);
         }

       //prepare user data to keep the connection
             this.connectionService = connectionService;
             this.userService.prepareConnection(this.connectionService);
       this.movieFromApi =
       {
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

  }

/*
  this handle the update
  of time of pause of the user
  */
  onSubmitPauseTime(movieWatched:watchedMovie){
    if(this.pauseTime == null){
      console.log("pause time vaut null et n'a pas été rempli"+this.pauseTime)
      }
    console.log('On entre dans OnSubmit pause time, le contenu de pause time est : '+this.pauseTime);
    let tmpHour:number = +(Array.from(this.pauseTime)[0]+Array.from(this.pauseTime)[1]);
    let tmpMinutes:number = +(Array.from(this.pauseTime)[3]+Array.from(this.pauseTime)[4]);
    let tmpSeconds:number = +(Array.from(this.pauseTime)[6]+Array.from(this.pauseTime)[7]);
    movieWatched.time.hours = tmpHour
    movieWatched.time.minutes = tmpMinutes
    movieWatched.time.seconds = tmpSeconds
    //movieWatched.timeOnly.minutes = Array.from(this.pauseTime.[0]
    this.movieService.updateMovieToUserInDataBaseWatchingTimeWatchedMovie(movieWatched,this.userService.userAccount);


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
        this.movieFromApi = data;
        /* on indique qu'on passe en mode informations complètes*/
        this.fullInfoOn = true;
      }
    });
  }

  /**
   add a movie to user watchList
   */
   addMovieToUserInDataBaseAsWatchLater(movieFull:watchedMovie, movieStatus:watchedMovieStatus){
     this.movieService.addMovieToUserInDataBaseAsWatchLaterWatchedMovie(movieFull, movieStatus, this.userService.userAccount);
     //relaod component to keep consistent page
     //this.reloadParentListComponent()
     //this.reloadCurrent();
     this.reloadPage();

   }


   /*
    addToWatchListAndDatabaseFromWatchedMovie(movie:watchedMovie, movieStatus:watchedMovieStatus){
      //this.userService.
      this.movieService.addMovieToUserInDataBaseAsWatchLater(movie, movieStatus, this.userService.userAccount);
    }*/

  /**
     remove from watchlater/watching/rewatch list
     */
    removeFromWatchListAndDataBaseAsWatchLater(movie:watchedMovie){

      //retrieve movie full info object
      this.completeInformations(movie.movie.imdbID);
        //this.userService.
        let movieRemoved:watchedMovie = this.movieService.removeMovieFromUserInDataBase(this.movieFromApi, this.userService.userAccount);


        //retrieve the movie as a watchedMovie
        //remove movie from movie list
        let index = this.movieService.userMoviesList.indexOf(movieRemoved);
        if(index!==-1){
          console.log("film retiré de la liste de l'utilisateur : "+movieRemoved.movie.imdbID);
          this.movieService.userMoviesList.splice(index,1);
        }

      //reload component to keep consistent page
      //this.reloadParentListComponent()
      //this.reloadCurrent();
      this.reloadPage();

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

 /**
   this method checks if the list of movie of the user in their watch later/watching/rewatch (depending
     on the argument sent) list contains the movie.

   */
  public listMovieContainsMovieWatchLater(movieWatched:watchedMovie, movieStatus:watchedMovieStatus){
    let movieSimple : Movie = {
              id:"0",
              title:movieWatched.movie.title,
              yearOfRelease:movieWatched.movie.yearOfRelease,
              director:movieWatched.movie.director,
              imdbID:movieWatched.movie.imdbID,
              poster: movieWatched.movie.poster
    };
    /*for(let i=0;i<this.movieService.userMoviesList.length;i++){
        console.log("les films présents dans la liste user : "+this.movieService.userMoviesList[i].movie.imdbID);
        if(this.movieService.userMoviesList[i].movie.imdbID === movieSimple.imdbID && this.movieService.userMoviesList[i].movieStatus === movieStatus){
          console.log("le film est déjà présent : "+this.movieService.userMoviesList[i].movie.imdbID+" il a pour status : "+this.movieService.userMoviesList[i].movieStatus);
          return true
        }
    }*/
    let statusAsNumber: number = +movieStatus;
    let status2AsNb : number = + movieWatched.movieStatus.valueOf();
    if(status2AsNb === statusAsNumber){
      return true;
    }
    //console.log("on va vérifier si le film : "+movie.imdbID+" title : "+movie.Title);
    return false;
  }

  /*
  this method reload the list containing
  all the movies.
  It's necessary when you update a movie data
  to keep the display consistent
  */
  reloadParentListComponent(){
    console.log("ok for reload parent");
    this.reloadComponent(false,"user/entrance/menuMovieChoice/listMovies")

  }


}
