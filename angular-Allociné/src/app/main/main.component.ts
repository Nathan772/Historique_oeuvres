import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MovieShortInformations } from '../model/models';
import { NavbarComponent } from '../navbar/navbar.component';
import {MovielistComponent} from '../movielist/movielist.component'
import { FilmServiceService } from '../service/film-service.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {

  
  /* cette variable va stocker les films
  retrouvés suite à la recherche */
  listFilms: MovieShortInformations[] = [];
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
  filmService: FilmServiceService;
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
  
    this.filmService.getMovieShort(chaine).subscribe((data) => {
      /* on s'assure que le film a bien été trouvé pour 
      afficher la chaine */
      /* on donne à "listFilms" la liste des 
      films associé au nom recherché par le user.*/
      if (data != null && data.length > 0) {
        console.log("la liste est non-vide");
        this.listFilms = data;
      }
    });

    /* on va émettre mais seulement si la liste de
    films est non-vide*/
      this.arrayFound.emit(this.listFilms);
  }

       /* l'initialisation
  du service dans le constructeur
  est indispensable*/
  constructor(service:FilmServiceService){
    this.filmService = service;
  }
  ngOnInit() {}
}
