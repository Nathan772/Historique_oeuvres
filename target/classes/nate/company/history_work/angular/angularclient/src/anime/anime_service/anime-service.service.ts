import { Injectable } from '@angular/core';
import { OrderByPipe } from '../../order-by/order-by.pipe';

/* pour faire cet import, il faut l'écrire en dur*/
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
/* pour faire cet import, il faut l'écrire en dur*/
import { Observable, of } from 'rxjs';
import { AnimeNavbarComponent } from '../navbar/anime-navbar/anime-navbar.component';
import {
  AnimeFullInformations,
  Anime,
  timeOnly,
  SearchResponse,
  watchedAnime,
  watchedAnimeStatus} from '../anime-models/anime-models';
import { User } from '../../user/user';
import { map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class AnimeServiceService {
private orderBy:OrderByPipe;
  private userAnimesUrl: string;
  public userAnimesList:watchedAnime[] = [];
  //créer une nouvelle catégorie qui contiendrait les status de visionnage des films
  //pour pouvoir ajouter un jeu de couleur et plus généralement, une correspodance.
  //public userMoviesStatus:MovieStatus[] = [];
  /*private registerUrl: string;
  public userAccount:User = {id:"",pseudo:"",email:"",password:""};
  private connectUrl:string;
  private userExistsUrl:string;*/
  statusWatching:string[] =
    ["en cours de visionnage",
      "à regarder plus tard", "fini", "à revoir"];



  constructor(private HttpClient: HttpClient, orderBy:OrderByPipe) {
        this.userAnimesUrl = 'http://localhost:8081/user/anime';
        /*this.registerUrl = 'http://localhost:8080/register';
        this.connectUrl = 'http://localhost:8080/connect';
        this.userExistsUrl = 'http://localhost:8080/userSearch';*/
        this.orderBy = orderBy;
  }

/*
this method retrieve the movies from the database and add them to the user list
*/
retrieveUserAnimes(userAccount:User){
            //empty user movie list to not have copy of the same movie
            this.userAnimesList = [];
              console.log("on récupère les animes du user nommé : "+userAccount.pseudo);
              this.findAllAnimesFromUserList(userAccount.pseudo, userAccount.password).subscribe((animes) => {
                /*
                 make sure the movie has been found before sending to animefull */
                    /* on donne à movieFull
                    les infos du film qui nous intéresse*/
                        if (animes != null){
                          console.log('les animes de l\'utilisateur '+userAccount.pseudo+'films ont bien été trouvé');
                          for(let animeWatched of animes){
                            /*console.log("on ajoute "+movie.title);
                            console.log("le imdb est : "+movie.imdbID);*/
                            //this.getMovieComplete(movieWatched.movie.imdbID).subscribe((movieComplete) => {
                              //console.log("film trouvé avec l'API : "+movieComplete.imdbID+ " "+movieComplete.Title);
                              console.log("l'heure de anime watched est : "+animeWatched.time.hours)
                              this.addToWatchList(animeWatched);
                            }
                          //);
                          }
                        }


                  );
                }

/*

deprecated

*/

addToWatchList(anime:watchedAnime){
      //this.userService.
      this.addAnimeToUserListWithoutDataBase(anime);
      //check if updated worked for user-connection
      /*for(let i=0;i<this.userMoviesList.length;i++){
        //console.log("les films présents : "+this.userMoviesList[i].movie.imdbID);
      }*/
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


  /* retrieve all the movies from user data present in the database. */
  public findAllAnimesFromUserList(userPseudo:string, userPassword:string):Observable<watchedAnime[]>{
    //deprecated (begin)
    //let params = new URLSearchParams(); deprecated
    //params.append("someParamKey", this.someParamValue)
     //let headers = new Headers();
       /* headers.append('Content-Type','application/json');
        headers.append()
        //let params = new URLSearchParams();
        //params.append("",)
        //let options = user ?
        { params: new HttpParams().set('userName', user) } : {};
        //const params = new HttpParams().set('','');
        return this.http.get<User>(this.userExistsUrl, { params: this.ToHttpParams(user)});*/
       //{params: new HttpParams.set('id',userId)}
       //let params = new HttpParams().set("id",userId); //Create new HttpParams
      //deprecated (end)

        //not completly functional
       /*let headers = new Headers();
       headers.append('Content-Type', 'application/json');
       let params = new URLSearchParams();
       return this.HttpClient.get<Movie[]>(this.userMoviesUrl, {params:this.ToHttpParams(userId)});*/



       //l

        /*interface UserId {
         pseudo:string;
       }

     let userInfo:UserId = {
       pseudo:string
      }*/
    const headers = new HttpHeaders().append('header', 'value');
    /*
        prepares params source
        https://stackoverflow.com/questions/44280303/angular-http-get-with-parameter
        */
    const params = new HttpParams().set('pseudo', userPseudo).set('password', userPassword);
    //current user is know by the backend actually

    //old doesn't use json format
    //return this.HttpClient.get<watchedMovie[]>(this.userMoviesUrl,  {headers: headers, params: params});
    //new use JsonFormat
    console.log("on passe bien par get service watched animes qu'on a modifié");
    return this.HttpClient.get<watchedAnime[]>(this.userAnimesUrl,  {headers: headers, params: params});
     //jsonStr = FROMJSONCONVERTER.writeValueAsString(new WatchedMovieDto(alreadyWatchedMovie));
      //return this.HttpClient.get<Movie[]>(this.userMoviesUrl,{params:this.ToHttpParams(user)});
  }











  /**
   this method checks if the list of movie of the user in their watch list contains the movie
   and if the movie is currently watched.
   */
  public listAnimeContainsWatching(anime:AnimeFullInformations){
    let animeSimple : Anime = {
              id:"0",
              Title:anime.Title,
              Year:anime.Year,
              imdbID:anime.imdbID,
              Poster: anime.Poster
    };
  /* useless, just for tests
    for(let i=0;i<this.userMoviesList.length;i++){
        console.log("les films présents dans la liste user : "+this.userMoviesList[i].movie.imdbID);
        if(this.userMoviesList[i].movie.imdbID === movie.imdbID && this.userMoviesList[i].movieStatus === watchedMovieStatus.WATCHING){

          console.log("le film est déjà présent en mode à revoir : "+this.userMoviesList[i].movie.imdbID);
          //return true;
        }
    }*/
    //console.log("on va vérifier si le film : "+movie.imdbID+" title : "+movie.Title);
    return false;
  }













/**
   this method checks if the list of movie of the user in their rewatch list contains the movie.

   */
   /*
  public listMovieContainsMovieRewatchLater(movie:MovieFullInformations){
    let movieSimple : Movie = {
              id:"0",
              title:movie.Title,
              yearOfRelease:movie.Year,
              director:movie.Director,
              imdbID:movie.imdbID,
              poster: movie.Poster
    };
    for(let i=0;i<this.userMoviesList.length;i++){
        console.log("les films présents dans la liste user : "+this.userMoviesList[i].movie.imdbID);
        if(this.userMoviesList[i].movie.imdbID === movie.imdbID && this.userMoviesList[i].movieStatus === watchedMovieStatus.REWATCH){
          console.log("le film est déjà présent : "+this.userMoviesList[i].movie.imdbID);
          return true
        }
    }
    //console.log("on va vérifier si le film : "+movie.imdbID+" title : "+movie.Title);
    return false;
  }*/

/**
   this method checks if the list of movie of the user in their watch later/watching/rewatch (depending
     on the argument sent) list contains the movie.
     It's a necessary to have two methods,
     because movieFull, only is fueled when you click on the full info button,
     before that it's empty.


   */
  public listAnimeContainsAnimeWatchLater(anime:Anime, animeStatus:watchedAnimeStatus){
    let animeSimple : Anime = {
              id:"0",
              Title:anime.Title,
              Year:anime.Year,
              imdbID:anime.imdbID,
              Poster: anime.Poster,
    };
    for(let i=0;i<this.userAnimesList.length;i++){
       // console.log("les films présents dans la liste user : "+this.userMoviesList[i].movie.imdbID);
        if(this.userAnimesList[i].anime.imdbID === anime.imdbID && this.userAnimesList[i].animeStatus === animeStatus){
          //console.log("le film est déjà présent : "+this.userMoviesList[i].movie.imdbID);
          return true
        }
    }
    //console.log("on va vérifier si le film : "+movie.imdbID+" title : "+movie.Title);
    return false;
  }









/**
   this method checks if the list of movie of the user in their watch later/watching/rewatch (depending
     on the argument sent) list contains the movie.
     It's a necessary to have two methods,
     because movieFull, only is fueled when you click on the full info button,
     before that it's empty.

   */
  public listAnimeContainsAnimeWatchLaterWatchedAnime(watchedAnime:watchedAnime, animeStatus:watchedAnimeStatus){
    let animeSimple : Anime = {
              id:"0",
              Title:watchedAnime.anime.Title,
              Year:watchedAnime.anime.Year,
              imdbID:watchedAnime.anime.imdbID,
              Poster: watchedAnime.anime.Poster,
    };
    for(let i=0;i<this.userAnimesList.length;i++){
        //console.log("les films présents dans la liste user : "+this.userMoviesList[i].movie.imdbID);
        if(this.userAnimesList[i].anime.imdbID === watchedAnime.anime.imdbID && this.userAnimesList[i].animeStatus === animeStatus){
          //console.log("le film est déjà présent : "+this.userMoviesList[i].movie.imdbID);
          return true
        }
    }
    //console.log("on va vérifier si le film : "+movie.imdbID+" title : "+movie.Title);
    return false;
  }



  /**
   this method checks if the list of movie of the user in their watch later/watching/rewatch (depending
     on the argument sent) list contains the movie.

   */
   /*
  public listMovieContainsMovieWatchLater(movie:MovieFullInformations, movieStatus:watchedMovieStatus){
    let movieSimple : Movie = {
              id:"0",
              title:movie.Title,
              yearOfRelease:movie.Year,
              director:movie.Director,
              imdbID:movie.imdbID,
              poster: movie.Poster
    };
    for(let i=0;i<this.userMoviesList.length;i++){
        console.log("les films présents dans la liste user : "+this.userMoviesList[i].movie.imdbID);
        if(this.userMoviesList[i].movie.imdbID === movie.imdbID && this.userMoviesList[i].movieStatus === movieStatus){
          console.log("le film est déjà présent : "+this.userMoviesList[i].movie.imdbID);
          return true
        }
    }
    //console.log("on va vérifier si le film : "+movie.imdbID+" title : "+movie.Title);
    return false;
  }*/


/**
   this method checks if the list of movie of the user in their
   "currently watching list" contains the movie.

   */
   /*
  public listMovieContainsMovieWatching(movie:MovieFullInformations){
    let movieSimple : Movie = {
              id:"0",
              title:movie.Title,
              yearOfRelease:movie.Year,
              director:movie.Director,
              imdbID:movie.imdbID,
              poster: movie.Poster
    };
    for(let i=0;i<this.userMoviesList.length;i++){
        console.log("les films présents dans la liste user : "+this.userMoviesList[i].movie.imdbID);
        if(this.userMoviesList[i].movie.imdbID === movie.imdbID && this.userMoviesList[i].movieStatus === watchedMovieStatus.WATCHING){
          console.log("le film est déjà présent : "+this.userMoviesList[i].movie.imdbID);
          return true
        }
    }
    //console.log("on va vérifier si le film : "+movie.imdbID+" title : "+movie.Title);
    return false;
  }*/
/**
   This method add a movie into the database
   */





  /**
   This method add a movie from the database
   */
  addAnimeToUserListWithoutDataBase(anime:watchedAnime){

        //une solution serait de retirer
        // le champ genre de movie movieSimple
        //
        /*let movieSimple : Movie = {
          id:"0",
          title:movie.Title,
          year:movie.Year,
          director:movie.Director,
          imdbID:movie.imdbID
        };*/
        //console.log("les données de temps du film sont, hours : "+movie.time.hours )

        //add anime to anime's list as a completely new anime
        if(this.userAnimesList.findIndex((animeIntoList) => animeIntoList.anime.Year === anime.anime.Year

        &&
        animeIntoList.anime.Title === anime.anime.Title

        ) < 0){
          console.log("la size de la liste avant le simple push sans élimination de doublon : "+this.userAnimesList.length);
          this.userAnimesList.push(anime);
           console.log("la size de la liste après le simple push sans élimination de doublon : "+this.userAnimesList.length);
          console.log("ajout dans la liste du user du film comme un nouveau anime "+anime.anime.Title+" : succès !");
        }
        else{
          //remove the old element
          console.log("la size de la liste avant le filter du statut : "+this.userAnimesList.length);
          this.userAnimesList = this.userAnimesList.filter(animeComplex =>
            !(
            animeComplex.anime.Title === anime.anime.Title
            && animeComplex.anime.Year === anime.anime.Year
            ));
          /*for(let movieWatched of this.userMoviesList){
            console.log("le watched movie contenu dans la liste a pour ttitre est : "+movieWatched.movie.title);
          }*/
          console.log("les infos de l'anime problématique : title : "+anime.anime.Title+ " year of release "+anime.anime.Year);

          console.log("la size de la liste après le filter du statut mais avant l'ajout : "+this.userAnimesList.length);
          this.userAnimesList.push(anime);
          console.log("la size de la liste après le filter du statut et après l'ajout : "+this.userAnimesList.length);
          console.log("l'anime "+anime.anime.Title+"est déjà présent dans la liste, pas de double ajout, donc on supprime l'ancien avant de remplacer");
        }

        /*for(let i=0;i<this.userMoviesList.length;i++){
              console.log("les films présents : "+this.userMoviesList[i].imdbID);
        }*/

      /*
      order the list in the asc direction
      */

      this.userAnimesList = this.orderBy.transformAnime(this.userAnimesList, 'asc')

  }


/*

add
or update the movie
in the user list for the database
*/
  addAnimeToUserInDataBaseAsWatchLater(anime:AnimeFullInformations, animeActualStatus:watchedAnimeStatus, user:User){

          console.log("l'année de sortie du film  est :"+anime.Year+" et son titre est : "+anime.Title);
          //une solution serait de retirer
          // le champ genre de movie movieSimple
          //
          let animeSimple : Anime = {
            id:"0",
            Title:anime.Title,
            Year:anime.Year,
            imdbID:anime.imdbID,
            Poster:anime.Poster
          };

          let watchedAnime:watchedAnime = {
            anime:animeSimple,
             animeStatus:animeActualStatus,
              time: {
                hours:0,
                minutes:0,
                seconds:0,

                },

            };

          let userSimple = {
                     pseudo:user.pseudo,
                     password:user.password
                   };

          //add movie to movie list
          //this.userMoviesList.push(movie);

          console.log("On sauvegarde l'anime dans la liste des animés de l'utilisateur : "+animeSimple.Title+" avec pour IMDB "+animeSimple.imdbID
            +"le status du watch later est : "+animeActualStatus);
          /*
          problème ici !!!!! :...

          */


          /*
          https://stackoverflow.com/questions/46707073/how-to-pass-multiple-json-object-parameters-to-http-post-method-in-angular4
          prepare jsons as params
          */
          //const headers = new HttpHeaders().append('header', 'value');
              /*
              prepares params source
              https://stackoverflow.com/questions/44280303/angular-http-get-with-parameter
              */


          /*
          wrapper for two objects in
          one
          component
          */

          /*
          create wrapper as string
          */

          this.HttpClient.post<String>(this.userAnimesUrl+'/add',{watchedAnime,userSimple})
                            .subscribe(
                                  animeRetrievedAsJson => {
                                    //save succeed
                                    this.addAnimeToUserListWithoutDataBase(watchedAnime);
                                    return animeRetrievedAsJson;
                                  }
                                );

    }

  addAnimeUserInDataBaseAsWatchLaterWatchedMovie(watchedAnime1:watchedAnime, animeActualStatus:watchedAnimeStatus, user:User){

                                                console.log("l'année de sortie du film  est :"+watchedAnime1.anime.Year+" et son titre est : "+watchedAnime1.anime.
                                                  Title);
                                                //une solution serait de retirer
                                                // le champ genre de movie movieSimple
                                                //
                                                let animeSimple : Anime = {
                                                  id:"0",
                                                  Title:watchedAnime1.anime.Title,
                                                  Year:watchedAnime1.anime.Year,
                                                  //Director:watchedAnime1.anime.Director,
                                                  imdbID:watchedAnime1.anime.imdbID,
                                                  Poster:watchedAnime1.anime.Poster
                                                };

                                                let watchedAnime:watchedAnime = {
                                                  anime:animeSimple,
                                                   animeStatus:animeActualStatus,
                                                    time: {
                                                      hours:0,
                                                      minutes:0,
                                                      seconds:0,

                                                      },

                                                  };

                                                let userSimple = {
                                                           pseudo:user.pseudo,
                                                           password:user.password
                                                         };

                                                //add movie to movie list
                                                //this.userMoviesList.push(movie);

                                                console.log("On sauvegarde l'anime dans la liste des animés de l'utilisateur : "+animeSimple.Title+" avec pour IMDB "+animeSimple.imdbID
                                                  +"le status du watch later est : "+animeActualStatus);
                                                /*
                                                problème ici !!!!! :...

                                                */


                                                /*
                                                https://stackoverflow.com/questions/46707073/how-to-pass-multiple-json-object-parameters-to-http-post-method-in-angular4
                                                prepare jsons as params
                                                */
                                                //const headers = new HttpHeaders().append('header', 'value');
                                                    /*
                                                    prepares params source
                                                    https://stackoverflow.com/questions/44280303/angular-http-get-with-parameter
                                                    */


                                                /*
                                                wrapper for two objects in
                                                one
                                                component
                                                */

                                                /*
                                                create wrapper as string
                                                */

                                                this.HttpClient.post<String>(this.userAnimesUrl+'/add',{watchedAnime,userSimple})
                                                                  .subscribe(
                                                                        animeRetrievedAsJson => {
                                                                          //save succeed
                                                                          console.log("on sauvegarde le nouvel état non vide de l'anime : "+animeRetrievedAsJson)
                                                                          this.addAnimeToUserListWithoutDataBase(watchedAnime);
                                                                          return animeRetrievedAsJson;
                                                                        }
                                                                      );

                                          }










  updateAnimeToUserInDataBaseWatchingTimeWatchedAnime(watchedAnime:watchedAnime, user:User){

                                                console.log("on met à jour l'anime en cours de visionnage. L'année de sortie de l'anime  est :"+watchedAnime.anime.Year+" et son titre est : "+watchedAnime.anime.Title);


                          /*
                          this copy
                          prevent from the copy of infinite nested
                          1001
                          */
                                                 let watchedAnime2:watchedAnime = {
                                                   anime:watchedAnime.anime,
                                                   animeStatus:watchedAnime.animeStatus,
                                                    time:watchedAnime.time,

                                                                                                     };

                                               let userSimple = {
                                                 pseudo:user.pseudo,
                                                 password:user.password
                                               };




                                                //add anime to anime list of the user
                                                //this.userMoviesList.push(movie);
                                                this.HttpClient.post<String>(this.userAnimesUrl+'/add',{watchedAnime2,userSimple})
                                                                  .subscribe(
                                                                        animeRetrievedAsJson => {
                                                                          //save succeed
                                                                          console.log("on sauvegarde le nouvel état non vide du film : "+animeRetrievedAsJson)
                                                                          this.addAnimeToUserListWithoutDataBase(watchedAnime2);
                                                                          return animeRetrievedAsJson;
                                                                        }
                                                                      );

                                          }





//NOT RELATED TO DATABASE






  /* fonction qui prend un argument le titre d'un film
  et qui récupère un observable qui contient les infos du films : titre, année, etc...*/
 public getAnimeShort(searchValue: string): Observable<Anime[]> {
    let myAnimeObservable: Observable<Anime[]> =
      this.HttpClient.get<SearchResponse>(
        'https://www.omdbapi.com/?apikey=1069fcf1&s=' + searchValue
      ).pipe(map((SearchResponse) => SearchResponse.Search));

    return myAnimeObservable;
    //les [...] permettetent de faire
    //une copie du tableau
  }



  /*
  a function that takes as an argument the movie's id, and retrieve an observable
  that contains the complete informations about the anime : title, year, etc...c...*/
  getAnimeComplete(imdbID: string): Observable<AnimeFullInformations> {
    let myAnimeObservable: Observable<AnimeFullInformations> =
      this.HttpClient.get<AnimeFullInformations>(
        'https://www.omdbapi.com/?apikey=1069fcf1&i=' + imdbID
      ).pipe(map((AnimeFullInformations) => AnimeFullInformations));

    return myAnimeObservable;
    //les [...] permettetent de faire
    //une copie du tableau
  }



  public randomDisplay(): void {
    console.log("c'est très réel");
  }


//can I delete with the same pattern as POST which mean : wrapper ???

public removeAnimeFromUserInDataBase(anime:AnimeFullInformations, user:User):watchedAnime {

          let animeSimple : Anime = {
            id:"0",
            Title:anime.Title,
            Year:anime.Year,
            imdbID:anime.imdbID,
            Poster: anime.Poster
          };

          let userSimple = {
            pseudo:user.pseudo,
            password:user.password
          };

          this.userAnimesList = this.userAnimesList.filter(
            animeKept => animeKept.anime.imdbID != anime.imdbID
          )
          console.log("On supprime le film dans la liste des films de l'utilisateur : "+user.pseudo+" avec pour IMDB "+animeSimple.imdbID);
          /*
          functional but no checking*/

          const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

          /*

          it works !
          Solution by chatgpt

          */
          let animeRemoved:watchedAnime ={
            anime:animeSimple,
            animeStatus:watchedAnimeStatus.WATCHLATER,
               time: {
                                        hours:0,
                                        minutes:0,
                                        seconds:0,

                                        },

            };

             console.log("les infos de l'anime à supprimer sont : imdb : "+animeSimple.imdbID+" title : "+animeSimple.Title)

           this.HttpClient.delete<string>(this.userAnimesUrl+"/remove",{
            headers,
            body: {animeRemoved, userSimple}
            })
          .subscribe(animeRemoved2 =>
                    {
          });
        return animeRemoved;

    }

  //can I delete with the same pattern as POST which mean : wrapper ???
  public removeMovieFromUserInDataBaseWithMovieObject(anime:Anime, user:User):watchedAnime {

            let animeSimple = anime;

            let userSimple = {
              pseudo:user.pseudo,
              password:user.password
            };

            this.userAnimesList = this.userAnimesList.filter(
              animeKept => animeKept.anime.imdbID != anime.imdbID
            )
            console.log("On supprime l'anime dans la liste des films de l'utilisateur : "+user.pseudo+" avec pour IMDB "+animeSimple.imdbID);
            /*
            functional but no checking*/

            const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

            /*

            it works !
            Solution by chatgpt

            */
            let animeRemoved:watchedAnime ={
              anime:animeSimple,
              animeStatus:watchedAnimeStatus.WATCHLATER,
                 time: {
                                          hours:0,
                                          minutes:0,
                                          seconds:0,

                                          },

              };

               console.log("les infos du film à supprimer sont : imdb : "+animeSimple.imdbID+" title : "+animeSimple.Title)

             this.HttpClient.delete<string>(this.userAnimesUrl+"/remove",{
              headers,
              body: {animeRemoved, userSimple}
              })
            .subscribe(animeRemoved2 =>
                      {
            });
          return animeRemoved;

      }

}
