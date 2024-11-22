import { Injectable } from '@angular/core';
/* pour faire cet import, il faut l'écrire en dur*/
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
/* pour faire cet import, il faut l'écrire en dur*/
import { Observable, of } from 'rxjs';
import { NavbarSeriesComponent } from '../navbar/navbar-series.component';
import {
  SerieFullInformations,
  SerieShortInformations,
  Serie,
  SearchResponse,
} from '../serie_models/serie_models';

import { User } from '../../user/user';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root' /* indispensable
  permet de dire où chercher la racine du projet*/,
})
export class SerieServiceService {

  private userSeriesUrl: string;
  public userSeriesList:SerieFullInformations[] = [];
  //créer une nouvelle catégorie qui contiendrait les status de visionnage des films
  //pour pouvoir ajouter un jeu de couleur et plus généralement, une correspodance.
  //public userSeriesStatus:SerieStatus[] = [];
  /*private registerUrl: string;
  public userAccount:User = {id:"",pseudo:"",email:"",password:""};
  private connectUrl:string;
  private userExistsUrl:string;*/
  statusWatching:string[] =
    ["en cours de visionnage",
      "à regarder plus tard", "fini", "à revoir"];



  constructor(private HttpClient: HttpClient) {
        this.userSeriesUrl = 'http://localhost:8080/user/serie';
        /*this.registerUrl = 'http://localhost:8080/register';
        this.connectUrl = 'http://localhost:8080/connect';
        this.userExistsUrl = 'http://localhost:8080/userSearch';*/
        if(this.userSeriesList){

          }
  }

/*
this method retrieve the series from the database and add them to the user list
*/
retrieveUserSeries(userAccount:User){
            //empty user serie list to not have copy of the same serie
            this.userSeriesList = [];
              console.log("on récupère les films du user nommé : "+userAccount.pseudo+" et d'id : "+userAccount.id);
              this.findAllSeriesFromUserList(userAccount.id).subscribe((series) => {
                /*
                   on s'assure que le film a bien été trouvé
                   avant de l'affecter à this.serieFull*/
                    /* on donne à serieFull
                    les infos du film qui nous intéresse*/
                        if (series != null){
                          console.log('les films de l\'utilisateur '+userAccount.id+'films ont bien été trouvé');
                          for(let serie of series){
                            console.log("on ajoute "+serie.title);
                            console.log("le imdb est : "+serie.imdbID);
                            this.getserieComplete(serie.imdbID).subscribe((serieComplete) => {
                              console.log("film trouvé avec l'API : "+serieComplete.imdbID+ " "+serieComplete.Title);
                              this.addToWatchList(serieComplete);
                            });
                          }
                        }

                    }
                  );
            }


addToWatchList(serie:SerieFullInformations){
      //this.userService.
      this.addSerieToUserListWithoutDataBase(serie);
      //check if updated worked for user-connection
      for(let i=0;i<this.userSeriesList.length;i++){
        console.log("les films présents : "+this.userSeriesList[i].imdbID);
      }
}


  //searchValue:string;







  //RELATED TO DATABASE








  /**
  this method enable to convert
  data to be readable by a get
  method
  from
  https://stackoverflow.com/questions/74699021/angular-14-http-get-request-pass-object-as-param
  */
  public ToHttpParams(request: any): HttpParams {
      let httpParams = new HttpParams();
      Object.keys(request).forEach(function (key) {
        httpParams = httpParams.append(key, request[key]);
      });
      return httpParams;
  }


  /* retrieve all the series from user data present in the database. */
  public findAllSeriesFromUserList(userId:number):Observable<Serie[]>{


        interface UserId {
         id:number;
       }

     let userInfo:UserId = {
       id:userId
      }
      return this.HttpClient.get<Serie[]>(this.userSeriesUrl,{params:this.ToHttpParams(userInfo)});
  }

  /**
   this method checks if the list of Serie of the user in their watch list contains the Serie.

   */
  public listSerieContainsSerie(serie:SerieFullInformations){
    let serieSimple : Serie = {
              id:"0",
              title:serie.Title,
              year:serie.Year,
              director:serie.Director,
              imdbID:serie.imdbID
    };
    for(let i=0;i<this.userSeriesList.length;i++){
        //console.log("les films présents : "+this.userSeriesList[i].imdbID);
        if(this.userSeriesList[i].imdbID === serie.imdbID){
          //console.log("le film est déjà présent : "+this.userSeriesList[i].imdbID);
          return true
        }
    }
    //console.log("on va vérifier si le film : "+serie.imdbID+" title : "+serie.Title);
    return false;
  }
/**
   This method add a serie into the database
   */





  /**
   This method add a serie into the database
   */
  addSerieToUserListWithoutDataBase(serie:SerieFullInformations){

        //une solution serait de retirer
        // le champ genre de Serie SerieSimple
        //


        //add serie to serie list
        if(this.userSeriesList.findIndex((serieIntoList) => serieIntoList === serie) < 0){
          this.userSeriesList.push(serie);
          console.log("ajout dans la liste du user du film "+serie.Title+" : succès !");
        }
        else{
          console.log("le film"+serie.Title+"est déjà présent dans la liste, pas de double ajout");
        }

        /*for(let i=0;i<this.userSeriesList.length;i++){
              console.log("les films présents : "+this.userSeriesList[i].imdbID);
        }*/

  }


  addSerieToUserInDataBase(serie:SerieFullInformations, status:String, user:User){

          //une solution serait de retirer
          // le champ genre de Serie SerieSimple
          //
          let serieSimple : Serie = {
            id:"0",
            title:serie.Title,
            year:serie.Year,
            director:serie.Director,
            imdbID:serie.imdbID
          };

          //add serie to serie list
          //this.userSeriesList.push(serie);

          console.log("On sauvegarde le film dans la liste des films de l'utilisateur : "+serieSimple.title+" avec pour IMDB "+serieSimple.imdbID);
          /*
          problème ici !!!!! :...
          */
          this.HttpClient.post<Serie>(this.userSeriesUrl+'/add',{"serie":serieSimple,"user":user})
                  .subscribe(
                        serieRetrieved => {
                          //save succeed
                          this.addSerieToUserListWithoutDataBase(serie)
                          return serieRetrieved;
                        }
                      );

    }














//NOT RELATED TO DATABASE






  /* fonction qui prend un argument le titre d'un film
  et qui récupère un observable qui contient les infos du films : titre, année, etc...*/
  getSerieShort(searchValue: string): Observable<SerieShortInformations[]> {
    let mySerieObservable: Observable<SerieShortInformations[]> =
      this.HttpClient.get<SearchResponse>(
        'https://www.omdbapi.com/?apikey=1069fcf1&s=' + searchValue
      ).pipe(map((SearchResponse) => SearchResponse.Search));

    return mySerieObservable;
    //les [...] permettetent de faire
    //une copie du tableau
  }

  /* fonction qui prend un argument l'id d'un film
  et qui récupère un observable qui contient les infos complète du films : titre, année, etc...*/
  getSerieComplete(idFilm: string): Observable<SerieFullInformations> {
    let mySerieObservable: Observable<SerieFullInformations> =
      this.HttpClient.get<SerieFullInformations>(
        'https://www.omdbapi.com/?apikey=1069fcf1&i=' + idFilm
      ).pipe(map((SerieFullInformations) => SerieFullInformations));

    return mySerieObservable;
    //les [...] permettetent de faire
    //une copie du tableau
  }



  public randomDisplay(): void {
    console.log("c'est très réel");
  }

//can I delete with the same pattern as POST which mean : wrapper ???
public removeSerieFromUserInDataBase(serie:SerieFullInformations, user:User){

          let serieSimple : Serie = {
            id:"0",
            title:serie.Title,
            year:serie.Year,
            director:serie.Director,
            imdbID:serie.imdbID
          };

          this.userSeriesList = this.userSeriesList.filter(
            serieKept => serieKept.imdbID != serie.imdbID
          )
          console.log("On supprime le film dans la liste des films de l'utilisateur : "+user.id+" avec pour IMDB "+serieSimple.imdbID);
          /*
          functional but no checking*/
          this.HttpClient.delete<String>(this.userSeriesUrl+"/remove/"+user.id+"/"+serieSimple.imdbID)
          .subscribe(response =>
                    {
                      if(response != null){
                      //remove from list
                      /*if(index!==-1){
                        console.log("film retiré de la liste de l'utilisateur : "+movie.imdbID);
                        this.userMoviesList.splice(index,1);
                      }*/
                    }
                    //console.log(movie.Title+" a été supprimé de la liste du user : "+this.userService.userAccount)
          });

        //this.HttpClient.delete<Movie>(this.userMoviesUrl+"/remove/"+user.id+"/"+movieSimple.imdbID).map(response =>response.json()).pipe(catchError());

    }
}
