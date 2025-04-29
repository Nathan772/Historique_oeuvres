import { Injectable } from '@angular/core';
/* pour faire cet import, il faut l'écrire en dur*/
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
/* pour faire cet import, il faut l'écrire en dur*/
import { Observable, of } from 'rxjs';
import { NavbarMoviesComponent } from '../navbar/navbar-movies.component';
import {
  MovieFullInformations,
  MovieShortInformations,
  Movie,
  timeOnly,
  SearchResponse,
  watchedMovie,
  watchedMovieStatus,
} from '../movie_models/movie_models';

import { User } from '../../user/user';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root' /* indispensable
  permet de dire où chercher la racine du projet*/,
})
export class MovieServiceService {

  private userMoviesUrl: string;
  public userMoviesList:watchedMovie[] = [];
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



  constructor(private HttpClient: HttpClient) {
        this.userMoviesUrl = 'http://localhost:8081/user/movie';
        /*this.registerUrl = 'http://localhost:8080/register';
        this.connectUrl = 'http://localhost:8080/connect';
        this.userExistsUrl = 'http://localhost:8080/userSearch';*/
        if(this.userMoviesList){

          }
  }

/*
this method retrieve the movies from the database and add them to the user list
*/
retrieveUserMovies(userAccount:User){
            //empty user movie list to not have copy of the same movie
            this.userMoviesList = [];
              console.log("on récupère les films du user nommé : "+userAccount.pseudo);
              this.findAllMoviesFromUserList(userAccount.pseudo, userAccount.password).subscribe((movies) => {
                /*
                   on s'assure que le film a bien été trouvé
                   avant de l'affecter à this.movieFull*/
                    /* on donne à movieFull
                    les infos du film qui nous intéresse*/
                        if (movies != null){
                          console.log('les films de l\'utilisateur '+userAccount.pseudo+'films ont bien été trouvé');
                          for(let movieWatched of movies){
                            /*console.log("on ajoute "+movie.title);
                            console.log("le imdb est : "+movie.imdbID);*/
                            //this.getMovieComplete(movieWatched.movie.imdbID).subscribe((movieComplete) => {
                              //console.log("film trouvé avec l'API : "+movieComplete.imdbID+ " "+movieComplete.Title);
                              this.addToWatchList(movieWatched);
                            }
                          //);
                          }
                        }


                  );
                }

/*

deprecated

*/

addToWatchList(movie:watchedMovie){
      //this.userService.
      this.addMovieToUserListWithoutDataBase(movie);
      //check if updated worked for user-connection
      for(let i=0;i<this.userMoviesList.length;i++){
        console.log("les films présents : "+this.userMoviesList[i].movie.imdbID);
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


  /* retrieve all the movies from user data present in the database. */
  public findAllMoviesFromUserList(userPseudo:string, userPassword:string):Observable<watchedMovie[]>{
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
    return this.HttpClient.get<watchedMovie[]>(this.userMoviesUrl,  {headers: headers, params: params});
      //return this.HttpClient.get<Movie[]>(this.userMoviesUrl,{params:this.ToHttpParams(user)});
  }











  /**
   this method checks if the list of movie of the user in their watch list contains the movie
   and if the movie is currently watched.
   */
  public listMovieContainsWatching(movie:MovieFullInformations){
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

          console.log("le film est déjà présent en mode à revoir : "+this.userMoviesList[i].movie.imdbID);
          //return true;
        }
    }
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
  public listMovieContainsMovieWatchLater(movie:MovieShortInformations, movieStatus:watchedMovieStatus){
    let movieSimple : Movie = {
              id:"0",
              title:movie.Title,
              yearOfRelease:movie.Year,
              imdbID:movie.imdbID,
              poster: movie.Poster,
              director: "unknown"
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
  }









/**
   this method checks if the list of movie of the user in their watch later/watching/rewatch (depending
     on the argument sent) list contains the movie.
     It's a necessary to have two methods,
     because movieFull, only is fueled when you click on the full info button,
     before that it's empty.

   */
  public listMovieContainsMovieWatchLaterWatchedMovie(watchedMovie:watchedMovie, movieStatus:watchedMovieStatus){
    let movieSimple : Movie = {
              id:"0",
              title:watchedMovie.movie.title,
              yearOfRelease:watchedMovie.movie.yearOfRelease,
              imdbID:watchedMovie.movie.imdbID,
              poster: watchedMovie.movie.poster,
              director: "unknown"
    };
    for(let i=0;i<this.userMoviesList.length;i++){
        console.log("les films présents dans la liste user : "+this.userMoviesList[i].movie.imdbID);
        if(this.userMoviesList[i].movie.imdbID === watchedMovie.movie.imdbID && this.userMoviesList[i].movieStatus === movieStatus){
          console.log("le film est déjà présent : "+this.userMoviesList[i].movie.imdbID);
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
  addMovieToUserListWithoutDataBase(movie:watchedMovie){

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
        console.log("les données de temps du film sont, hours : "+movie.time.hours )

        //add movie to movie list as a completely new movie
        if(this.userMoviesList.findIndex((movieIntoList) => movieIntoList.movie.yearOfRelease === movie.movie.yearOfRelease
        &&
        movieIntoList.movie.director === movie.movie.director
        &&
        movieIntoList.movie.title === movie.movie.title

        ) < 0){
          console.log("la size de la liste avant le simple push sans élimination de doublon : "+this.userMoviesList.length);
          this.userMoviesList.push(movie);
           console.log("la size de la liste après le simple push sans élimination de doublon : "+this.userMoviesList.length);
          console.log("ajout dans la liste du user du film comme un nouveau film "+movie.movie.title+" : succès !");
        }
        else{
          //remove the old element
          console.log("la size de la liste avant le filter du statut : "+this.userMoviesList.length);
          this.userMoviesList = this.userMoviesList.filter(movieComplex => movieComplex.movie.director === movie.movie.director
            && movieComplex.movie.title === movie.movie.title
            && movieComplex.movie.yearOfRelease === movie.movie.yearOfRelease
            );
          console.log("la size de la liste après le filter du statut mais avant l'ajout : "+this.userMoviesList.length);
          this.userMoviesList.push(movie);
          console.log("la size de la liste après le filter du statut et après l'ajout : "+this.userMoviesList.length);
          console.log("le film"+movie.movie.title+"est déjà présent dans la liste, pas de double ajout, donc on supprime l'ancien avant de remplacer");
        }

        /*for(let i=0;i<this.userMoviesList.length;i++){
              console.log("les films présents : "+this.userMoviesList[i].imdbID);
        }*/

  }


/*

add
or update the movie
in the user list for the database
*/
  addMovieToUserInDataBaseAsWatchLater(movie:MovieFullInformations, movieActualStatus:watchedMovieStatus, user:User){

          console.log("l'année de sortie du film  est :"+movie.Year+" et son titre est : "+movie.Title);
          //une solution serait de retirer
          // le champ genre de movie movieSimple
          //
          let movieSimple : Movie = {
            id:"0",
            title:movie.Title,
            yearOfRelease:movie.Year,
            director:movie.Director,
            imdbID:movie.imdbID,
            poster:movie.Poster
          };

          let watchedMovie:watchedMovie = {
            movie:movieSimple,
             movieStatus:movieActualStatus,
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

          console.log("On sauvegarde le film dans la liste des films de l'utilisateur : "+movieSimple.title+" avec pour IMDB "+movieSimple.imdbID
            +"le status du watch later est : "+movieActualStatus);
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

          this.HttpClient.post<String>(this.userMoviesUrl+'/add',{watchedMovie,userSimple})
                            .subscribe(
                                  movieRetrievedAsJson => {
                                    //save succeed
                                    this.addMovieToUserListWithoutDataBase(watchedMovie);
                                    return movieRetrievedAsJson;
                                  }
                                );

    }

  addMovieToUserInDataBaseAsWatchLaterWatchedMovie(watchedMovie1:watchedMovie, movieActualStatus:watchedMovieStatus, user:User){

                                                console.log("l'année de sortie du film  est :"+watchedMovie1.movie.yearOfRelease+" et son titre est : "+watchedMovie1.movie.
                                                  title);
                                                //une solution serait de retirer
                                                // le champ genre de movie movieSimple
                                                //
                                                let movieSimple : Movie = {
                                                  id:"0",
                                                  title:watchedMovie1.movie.title,
                                                  yearOfRelease:watchedMovie1.movie.yearOfRelease,
                                                  director:watchedMovie1.movie.director,
                                                  imdbID:watchedMovie1.movie.imdbID,
                                                  poster:watchedMovie1.movie.poster
                                                };

                                                let watchedMovie:watchedMovie = {
                                                  movie:movieSimple,
                                                   movieStatus:movieActualStatus,
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

                                                console.log("On sauvegarde le film dans la liste des films de l'utilisateur : "+movieSimple.title+" avec pour IMDB "+movieSimple.imdbID
                                                  +"le status du watch later est : "+movieActualStatus);
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

                                                this.HttpClient.post<String>(this.userMoviesUrl+'/add',{watchedMovie,userSimple})
                                                                  .subscribe(
                                                                        movieRetrievedAsJson => {
                                                                          //save succeed
                                                                          this.addMovieToUserListWithoutDataBase(watchedMovie);
                                                                          return movieRetrievedAsJson;
                                                                        }
                                                                      );

                                          }














//NOT RELATED TO DATABASE






  /* foncion qui prend un argument le titre d'un film
  et qui récupère un observable qui contient les infos du films : titre, année, etc...*/
 public getMovieShort(searchValue: string): Observable<MovieShortInformations[]> {
    let myMovieObservable: Observable<MovieShortInformations[]> =
      this.HttpClient.get<SearchResponse>(
        'https://www.omdbapi.com/?apikey=1069fcf1&s=' + searchValue
      ).pipe(map((SearchResponse) => SearchResponse.Search));

    return myMovieObservable;
    //les [...] permettetent de faire
    //une copie du tableau
  }

  /* fonction qui prend un argument l'id d'un film
  et qui récupère un observable qui contient les infos complète du films : titre, année, etc...*/
  getMovieComplete(idFilm: string): Observable<MovieFullInformations> {
    let myMovieObservable: Observable<MovieFullInformations> =
      this.HttpClient.get<MovieFullInformations>(
        'https://www.omdbapi.com/?apikey=1069fcf1&i=' + idFilm
      ).pipe(map((MovieFullInformations) => MovieFullInformations));

    return myMovieObservable;
    //les [...] permettetent de faire
    //une copie du tableau
  }



  public randomDisplay(): void {
    console.log("c'est très réel");
  }


//can I delete with the same pattern as POST which mean : wrapper ???
public removeMovieFromUserInDataBase(movie:MovieFullInformations, user:User):watchedMovie {

          let movieSimple : Movie = {
            id:"0",
            title:movie.Title,
            yearOfRelease:movie.Year,
            director:movie.Director,
            imdbID:movie.imdbID,
            poster: movie.Poster
          };

          let userSimple = {
            pseudo:user.pseudo,
            password:user.password
          };

          this.userMoviesList = this.userMoviesList.filter(
            movieKept => movieKept.movie.imdbID != movie.imdbID
          )
          console.log("On supprime le film dans la liste des films de l'utilisateur : "+user.pseudo+" avec pour IMDB "+movieSimple.imdbID);
          /*
          functional but no checking*/

          const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

          /*

          it works !
          Solution by chatgpt

          */
          let movieRemoved:watchedMovie ={
            movie:{id:"",title:"",yearOfRelease:"",director:"",imdbID:"",poster:""},
            movieStatus:watchedMovieStatus.WATCHLATER,
               time: {
                                        hours:0,
                                        minutes:0,
                                        seconds:0,

                                        },

            };
          /*
          let movieRemoved:watchedMovie = {
                      movie:movieSimple,
                       movieStatus:movieActualStatus,
                        time: {
                          hours:0,
                          minutes:0,
                          seconds:0,

                          },

                      };*/

           this.HttpClient.delete<string>(this.userMoviesUrl+"/remove",{
            headers,
            body: {movieSimple, userSimple}
            })
          .subscribe(movieRemoved2 =>
                    { //movieRemoved
                      //= movieRemoved2
                      /*
                      movie:{id:"",title:"",yearOfRelease:"",director:"",imdbID:"",poster:""},
                                  movieStatus:watchedMovieStatus.WATCHLATER,
                                     time: {
                                                              hours:0,
                                                              minutes:0,
                                                              seconds:0,

                                                              },

                                  };
                                */
                     // console.log("le film removed data :"+ {{ movieRemoved2 | json }});
                      //let jsonMovieRemoved = JSON.parse(movieRemoved2 | JSON)
                      /*deprecated
                      let jsonMovieRemoved = JSON.parse(movieRemoved2)
                      */
                      //console.log("l'état de jsonMovieRemoved : "+jsonMovieRemoved);
                      //movieRemoved.movie.id = jsonMovieRemoved.movie.id;
                      //console.log("l'état de movieRemoved après avoir reçu des données : "+movieRemoved);
                      /*if(response != null){
                      //remove from list
                      if(index!==-1){
                        console.log("film retiré de la liste de l'utilisateur : "+movie.imdbID);
                        this.userMoviesList.splice(index,1);
                      }*/
                    //console.log(movie.Title+" a été supprimé de la liste du user : "+this.userService.userAccount)
          });
        return movieRemoved;

        //this.HttpClient.delete<Movie>(this.userMoviesUrl+"/remove/"+user.id+"/"+movieSimple.imdbID).map(response =>response.json()).pipe(catchError());

    }


}
