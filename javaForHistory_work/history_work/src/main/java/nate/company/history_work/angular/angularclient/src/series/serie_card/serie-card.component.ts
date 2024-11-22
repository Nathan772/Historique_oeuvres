import { Component, Input, OnInit } from '@angular/core';
import { SerieFullInformations, SerieShortInformations } from '../serie_models/serie_models';
import { SerieServiceService } from '../serie_service/serie-service.service';
import { BrowserModule } from '@angular/platform-browser';
import { UserService } from '../../user/user_service/user-service.service';
@Component({
  selector: 'app-serie-card',
  templateUrl: './serie-card.component.html',
  styleUrls: ['./serie-card.component.css'],
})
export class SerieCardComponent implements OnInit {
  @Input()
  serie: SerieShortInformations;
  /*
  pour init ce champ, cela ne suffit
  pas il faut aussi l'initialiser au niveau
  du contructeur (voir constructeur)
  */
  serieService: SerieServiceService;
  @Input()
  serieFull: SerieFullInformations;
  fullInfoOn: boolean = false;
  userService:UserService;

  ngOnInit() {}

  /* l'initialisation
    du service dans le constructeur
    est indispensable*/
    constructor(serieService: SerieServiceService, userService:UserService) {
      this.serieService = serieService;
      this.userService = userService;
      this.serieFull = {
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
       this.serie = {
          Title: "",
           Year: "",
           imdbID: "",
           Type: "",
           Poster: ""
           }

         if(this.serieService.userSeriesList.length == 0){
            this.serieService.retrieveUserSeries(userService.userAccount);
         }

    }

  /* cette fonction donne les infos complètes
   sur un film à la variable serieFull en se servant d'un service. */
  completeInformations(imdbID: string) {
    this.serieService.getSerieComplete(imdbID).subscribe((data) => {
      /*
      on s'assure que le film a bien été trouvé
      avant de l'affecter à this.serieFull*/
      /* on donne à serieFull
      les infos du film qui nous intéresse*/
      if (data != null) {
        console.log('le film a bien été trouvé');
        this.serieFull = data;
        /* on indique qu'on passe en mode informations complètes*/
        this.fullInfoOn = true;
      }
    });
  }

  /**
   add a serie to user watchList
   */
    addToWatchListAndDatabase(serie:SerieFullInformations, status:String){
      //this.userService.
      this.serieService.addSerieToUserInDataBase(serie, status, this.userService.userAccount);
    }

    removeFromWatchListAndDataBase(serie:SerieFullInformations){
        this.serieService.removeSerieFromUserInDataBase(serie, this.userService.userAccount);
    }




  /* cette fonction va désactiver le mode full
  information*/
  disableFull() {
    this.fullInfoOn = false;
  }


}
