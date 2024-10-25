import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MovieServiceService } from '../movie_service/movie-service.service';
import { NavbarMoviesComponent } from '../navbar/navbar-movies.component';
import { MovieListComponent } from '../movie_list/movie-list.component';
import { MovieCardComponent } from '../movie_card/movie-card.component';
import { MovieShortInformations, SearchResponse, MovieFullInformations, Rating} from '../movie_models/movie_models'

@Component({
  selector: 'app-movie-search',
  templateUrl: './movie-search.component.html',
  styleUrl: './movie-search.component.scss'
})
export class MovieSearchComponent {

   /* cette variable va stocker les films
    retrouvés suite à la recherche */
    listMovies: MovieShortInformations[] = [];
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
          this.listMovies = data;
        }
      });

      /* on va émettre mais seulement si la liste de
      films est non-vide*/
        this.arrayFound.emit(this.listMovies);
    }

         /* l'initialisation
    du service dans le constructeur
    est indispensable*/
    constructor(service:MovieServiceService){
      this.movieService = service;
    }
    ngOnInit() {}
}
