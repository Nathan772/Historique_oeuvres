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
  SearchResponse,
} from '../movie_models/movie_models';

import { User } from '../../user/user';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root' /* indispensable
  permet de dire où chercher la racine du projet*/,
})
export class MovieServiceService {

  private userMoviesUrl: string;
  public userMoviesList:MovieFullInformations[] = [];
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
        this.userMoviesUrl = 'http://localhost:8080/user/movie';
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
              console.log("on récupère les films du user nommé : "+userAccount.pseudo+" et d'id : "+userAccount.id);
              this.findAllMoviesFromUserList(userAccount.id).subscribe((movies) => {
                /*
                   on s'assure que le film a bien été trouvé
                   avant de l'affecter à this.movieFull*/
                    /* on donne à movieFull
                    les infos du film qui nous intéresse*/
                        if (movies != null){
                          console.log('les films de l\'utilisateur '+userAccount.id+'films ont bien été trouvé');
                          for(let movie of movies){
                            console.log("on ajoute "+movie.title);
                            console.log("le imdb est : "+movie.imdbID);
                            this.getMovieComplete(movie.imdbID).subscribe((movieComplete) => {
                              console.log("film trouvé avec l'API : "+movieComplete.imdbID+ " "+movieComplete.Title);
                              this.addToWatchList(movieComplete);
                            });
                          }
                        }

                    }
                  );
            }


addToWatchList(movie:MovieFullInformations){
      //this.userService.
      this.addMovieToUserListWithoutDataBase(movie);
      //check if updated worked for user-connection
      for(let i=0;i<this.userMoviesList.length;i++){
        console.log("les films présents : "+this.userMoviesList[i].imdbID);
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
  public findAllMoviesFromUserList(userId:number):Observable<Movie[]>{
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

        interface UserId {
         id:number;
       }

     let userInfo:UserId = {
       id:userId
      }
      return this.HttpClient.get<Movie[]>(this.userMoviesUrl,{params:this.ToHttpParams(userInfo)});
  }

  /**
   this method checks if the list of movie of the user in their watch list contains the movie.

   */
  public listMovieContainsMovie(movie:MovieFullInformations){
    let movieSimple : Movie = {
              id:"0",
              title:movie.Title,
              year:movie.Year,
              director:movie.Director,
              imdbID:movie.imdbID
    };
    for(let i=0;i<this.userMoviesList.length;i++){
        //console.log("les films présents : "+this.userMoviesList[i].imdbID);
        if(this.userMoviesList[i].imdbID === movie.imdbID){
          //console.log("le film est déjà présent : "+this.userMoviesList[i].imdbID);
          return true
        }
    }
    //console.log("on va vérifier si le film : "+movie.imdbID+" title : "+movie.Title);
    return false;
  }
/**
   This method add a movie into the database
   */





  /**
   This method add a movie into the database
   */
  addMovieToUserListWithoutDataBase(movie:MovieFullInformations){

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

        //add movie to movie list
        this.userMoviesList.push(movie);
        console.log("ajout dans la liste du user : succès !");
        /*for(let i=0;i<this.userMoviesList.length;i++){
              console.log("les films présents : "+this.userMoviesList[i].imdbID);
        }*/

  }


  addMovieToUserInDataBase(movie:MovieFullInformations, status:String, user:User){

          //une solution serait de retirer
          // le champ genre de movie movieSimple
          //
          let movieSimple : Movie = {
            id:"0",
            title:movie.Title,
            year:movie.Year,
            director:movie.Director,
            imdbID:movie.imdbID
          };

          //add movie to movie list
          //this.userMoviesList.push(movie);

          console.log("On sauvegarde le film dans la liste des films de l'utilisateur : "+movieSimple.title+" avec pour IMDB "+movieSimple.imdbID);
          /*
          problème ici !!!!! :...
          */
          this.HttpClient.post<Movie>(this.userMoviesUrl+'/add',{"movie":movieSimple,"user":user})
                  .subscribe(
                        movieRetrieved => {
                          //save succeed
                          this.addMovieToUserListWithoutDataBase(movie)
                          return movieRetrieved;
                        }
                      );

    }














//NOT RELATED TO DATABASE






  /* fonction qui prend un argument le titre d'un film
  et qui récupère un observable qui contient les infos du films : titre, année, etc...*/
  getMovieShort(searchValue: string): Observable<MovieShortInformations[]> {
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
public removeMovieFromUserInDataBase(movie:MovieFullInformations, user:User){

          let movieSimple : Movie = {
            id:"0",
            title:movie.Title,
            year:movie.Year,
            director:movie.Director,
            imdbID:movie.imdbID
          };

          this.userMoviesList = this.userMoviesList.filter(
            movieKept => movieKept.imdbID != movie.imdbID
          )
          console.log("On supprime le film dans la liste des films de l'utilisateur : "+user.id+" avec pour IMDB "+movieSimple.imdbID);
          /*
          functional but no checking*/
          this.HttpClient.delete<String>(this.userMoviesUrl+"/remove/"+user.id+"/"+movieSimple.imdbID)
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
