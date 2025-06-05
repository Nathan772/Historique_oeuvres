import { Component, Input, OnInit } from '@angular/core';
import { CommonFunctionalityComponent } from '../../common-functionality-component/common-functionality-component.component';
import { Router } from '@angular/router';
import { Anime, AnimeFullInformations, watchedAnimeStatus} from '../anime-models/anime-models';
import { UserService } from '../../user/user_service/user-service.service';
import { AnimeServiceService } from '../anime_service/anime-service.service';
@Component({
  selector: 'app-anime-card',
  templateUrl: './anime-card.component.html',
  styleUrl: './anime-card.component.scss'
})
export class AnimeCardComponent implements OnInit{


    //movie: MovieShortInformations;
    @Input()
    anime: Anime;

    /*
    pour init ce champ, cela ne suffit
    pas il faut aussi l'initialiser au niveau
    du contructeur (voir constructeur)
    */
   animeService: AnimeServiceService;
   watchedAnimeStatus = watchedAnimeStatus;
    @Input()
    animeFull: AnimeFullInformations;
    fullInfoOn: boolean = false;
    userService:UserService;

    ngOnInit() {}

    /* l'initialisation
      du service dans le constructeur
      est indispensable*/
      constructor(animeService: AnimeServiceService, userService:UserService) {
        this.animeService = animeService;
        this.userService = userService;
        this.animeFull = {
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
            Writer:"",
            Actors:"",
           };
         this.anime = {
           id:"",
            Title: "",
             Year: "",
             imdbID: "",
             Poster: "",
             }

           if(this.animeService.userAnimesList.length == 0){
              this.animeService.retrieveUserAnimes(userService.userAccount);
           }

      }

    /* cette fonction donne les infos complètes
     sur un film à la variable movieFullen en se servant d'un service. */
    completeInformations(imdbID: string) {
      this.animeService.getAnimeComplete(imdbID).subscribe((data) => {
        /*
        on s'assure que le film a bien été trouvé
        avant de l'affecter à this.movieFull*/
        /* on donne à movieFull
        les infos du film qui nous intéresse*/
        if (data != null) {
          console.log('le film a bien été trouvé');
          this.animeFull = data;
          /* on indique qu'on passe en mode informations complètes*/
          this.fullInfoOn = true;
        }
      });
    }

    /**
     add a movie to user watchList
     */
      addAnimeToUserInDataBaseAsWatchLater(anime:AnimeFullInformations, animeStatus:watchedAnimeStatus){
        //this.userService.
        //remove from watch list if previous instances existed
        //this.removeFromWatchListAndDataBase(movie);
        this.animeService.addAnimeToUserInDataBaseAsWatchLater(anime, animeStatus, this.userService.userAccount);
        //reload component to keep consistent page
                   //window.location.reload();
      }



      removeFromWatchListAndDataBase(anime:AnimeFullInformations){
          //this.userService.
          this.animeService.removeAnimeFromUserInDataBase(anime, this.userService.userAccount);
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


