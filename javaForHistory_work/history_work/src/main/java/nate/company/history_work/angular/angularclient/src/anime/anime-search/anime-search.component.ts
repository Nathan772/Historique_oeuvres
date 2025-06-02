import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AnimeServiceService } from '../anime_service/anime-service.service';
import { NavbarMoviesComponent } from '../navbar/navbar-movies.component';
import { AnimeListComponent } from '../anime-list/anime-list.component';
import { AnimeCardComponent } from '../anime-card/anime-card.component';
import { Anime, SearchResponse, AnimeFullInformations, Rating} from '../anime-models/anime-models';
import {UserService } from '../../user/user_service/user-service.service';
import {ConnectionServiceService } from '../../connection/connection-service.service';
import { Anime } from '../anime_models/anime_models';

@Component({
  selector: 'app-anime-search',
  standalone: true,
  imports: [],
  templateUrl: './anime-search.component.html',
  styleUrl: './anime-search.component.scss'
})
export class AnimeSearchComponent {

   /* cette variable va stocker les films
      retrouvés suite à la recherche */
      listAnimesTransfer: Anime[] = [];
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
      animeService: AnimeServiceService;
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
      arrayFound = new EventEmitter<Anim[]>();

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

        this.animeService.getAnimeShort(chaine).subscribe((data) => {
          /* on s'assure que le film a bien été trouvé pour
          afficher la chaine */
          /* on donne à "listFilms" la liste des
          films associé au nom recherché par le user.*/
          if (data != null && data.length > 0) {
            console.log("la liste est non-vide");
            //Only accept movies and no other kind
            //this.listMoviesTransfer= data.filter(movie=>movie.Type === "movie");
            //enable everything ()
            //need to add in genre : Contains animation in anime.genre (in the fullinformation form)
            this.listAnimeTransfer= data.filter(anime=>anime.Type === "series";

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
          this.arrayFound.emit(this.listAnimesTransfer);
      }

           /* l'initialisation
      du service dans le constructeur
      est indispensable*/
      constructor(service:AnimeServiceService, userService:UserService, connectionService:ConnectionServiceService){
        this.animeService = service;
        this.userService = userService;
        //load user's animes
        if(this.animeService.userAnimesList.length == 0){
                    this.animeService.retrieveUserAnimes(this.userService.userAccount);
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
