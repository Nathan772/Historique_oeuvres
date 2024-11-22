import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { SerieServiceService } from '../serie_service/serie-service.service';
import { NavbarSeriesComponent } from '../navbar/navbar-series.component';
import { SerieListComponent } from '../serie_list/serie-list.component';
import { SerieCardComponent } from '../serie_card/serie-card.component';
import { SerieShortInformations, SearchResponse, SerieFullInformations, Rating} from '../serie_models/serie_models';
import {UserService } from '../../user/user_service/user-service.service';
import {ConnectionServiceService } from '../../connection/connection-service.service';

@Component({
  selector: 'app-serie-search',
  templateUrl: './serie-search.component.html',
  styleUrl: './serie-search.component.scss'
})
export class SerieSearchComponent {

   /* cette variable va stocker les films
    retrouvés suite à la recherche */
    listSeries: SerieShortInformations[] = [];
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
    serieService: SerieServiceService;
    userService:UserService;
    connectionService:ConnectionServiceService;
     /*on va utiliser cet émeteur pour émettre un tableau
    qui correspond à la liste de films
    trouvée par l'utilisateur lors de sa recherche.
    Il faut savoir que
    L'émetteur est possédé par celui qui émet l'information
    c'est à dire celui qui sait qu'il y a eu un
    événement et qui va avertir l'autre composant
    en lui donnant ces infos*/
    @Output()
    arrayFound = new EventEmitter<SerieShortInformations[]>();

    startSearch(chaine: string) {
      console.log('le user a recherché ' + chaine);
      /* Il faudra appeler une fonction du service serie-service
       this.search et l'utiliser pour faire la recherche dans la bdd*/

      this.serieService.getSerieShort(chaine).subscribe((data) => {
        /* on s'assure que le film a bien été trouvé pour
        afficher la chaine */
        /* on donne à "listFilms" la liste des
        films associé au nom recherché par le user.*/
        if (data != null && data.length > 0) {
          console.log("la liste est non-vide");
          //Only accept series and no other kind
          this.listSeries = data.filter(serie=>serie.Type === "serie");
        }
      });

      /* on va émettre mais seulement si la liste de
      films est non-vide*/
        this.arrayFound.emit(this.listSeries);
    }

         /* l'initialisation
    du service dans le constructeur
    est indispensable*/
    constructor(service:SerieServiceService, userService:UserService, connectionService:ConnectionServiceService){
      this.serieService = service;
      this.userService = userService;
      //load user's series
      if(this.serieService.userSeriesList.length == 0){
                  this.serieService.retrieveUserSeries(this.userService.userAccount);
      }
      //prepare user data to keep the connection
      this.connectionService = connectionService;
      this.userService.prepareConnection(this.connectionService);
      console.log("les données ont bien été récupérées, le user a pour pseudo :"+this.userService.userAccount.pseudo);
    }
    ngOnInit() {}
}
