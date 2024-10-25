import { Component, Input, OnInit } from '@angular/core';
import { MovieFullInformations, MovieShortInformations } from '../movie_models/movie_models';
import { MovieServiceService } from '../movie_service/movie-service.service';
import { BrowserModule } from '@angular/platform-browser';
import { UserService } from '../../user/user_service/user-service.service';
@Component({
  selector: 'app-movie-card',
  templateUrl: './movie-card.component.html',
  styleUrls: ['./movie-card.component.css'],
})
export class MovieCardComponent implements OnInit {
  @Input()
  movie: MovieShortInformations;
  /*
  pour init ce champ, cela ne suffit
  pas il faut aussi l'initialiser au niveau
  du contructeur (voir constructeur)
  */
  serviceMovie: MovieServiceService;
  movieFull: MovieFullInformations;
  fullInfoOn: boolean = false;
  userService:UserService;

  ngOnInit() {}

  /* l'initialisation
    du service dans le constructeur
    est indispensable*/
    constructor(movieService: MovieServiceService, userService:UserService) {
      this.serviceMovie = movieService;
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
           Type: "",
           Poster: ""
           }

    }

  /* cette fonction donne les infos complètes
   sur un film à la variable movieFullen en se servant d'un service. */
  completeInformations(imdbID: string) {
    this.serviceMovie.getMovieComplete(imdbID).subscribe((data) => {
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

  addToWatchList(imdbID:string, title:string, genre:string){
    //this.userService.
   }

  /* cette fonction va désactiver le mode full
  information*/
  disableFull() {
    this.fullInfoOn = false;
  }


}
