/*import { Component } from '@angular/core';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [],
  templateUrl: './user-form.component.html',
  styleUrl: './user-form.component.scss'
})
export class UserFormComponent {
généré automatiquement
}*/

/* repris du site baeldung */
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user_service/user-service.service';
import {MovieServiceService } from '../../movies/movie_service/movie-service.service';
import {MovieFullInformations} from '../../movies/movie_models/movie_models';
import { User,copyData } from '../user';
import { ConnectionServiceService } from "../../connection/connection-service.service";

@Component({
  selector: 'app-user-connexion',
  templateUrl: './user-connection.component.html',
  styleUrls: ['./user-connection.component.scss']
})


export class UserConnectionComponent implements OnInit {


  user: User;
  //copyData{passwordCopy:string};
   //= {id:"5", name:"jean",email:"jean"};
  router:Router;
  userService:UserService;
  copyInfo:copyData;
  connectionService:ConnectionServiceService;
  alreadyTried:Boolean;
  movieService:MovieServiceService;


  /*
  le user service du constructeur permet d'être utilisé
  pour sauvegarder le User offert.

  */
  constructor(routerParam: Router,service: UserService, connectionService:ConnectionServiceService, movieService:MovieServiceService ) {
    this.router = routerParam;
    this.userService =service;
    this.connectionService = connectionService;
    this.movieService = movieService;
    //this.mismatchedPassword = false;
    /*nécessaire même si sera remplacé
    car cela correspond aux valeurs par défaut de l'utilisateur
    même si l'id pourrait causer un conflit,
    en pratique ce n'est pas le cas
    car il est aussi automatiquement
    remplacé par la base de donnée
    par le suivant du serial !!
    */
    //this.alreadyExists = false;

    this.user = {id:51, pseudo:"", email:"", password:""};
    this.copyInfo = {passwordCopy:""};
    /*at the inception
    the user is not connected at all...
    */
    this.alreadyTried=false;
    this.connectionService.isConnected = false;
    this.connectionService.alreadyExists = false
    this.connectionService.mismatchedPassword = false;
  }


  connectUser() {
    console.log("try to connect user");
    //check if user exists in data base
    this.userService.findUser(this.user).subscribe(
        userFound => {

          //The user already exists
          if(userFound != null){
              console.log("le user a été trouvé ! son id : "+userFound.id+ " son pseudo : "+userFound.pseudo);
              //the user exists, starts connexion
              if(userFound.password == this.user.password){
                 //connect the user + retrieve infos
                 this.connectionService.isConnected = true;
                 //update the actual user with their true info (notably the uid)
                 this.userService.userAccount = userFound;
                 console.log("le pseudo du user connecté est : "+userFound);
                 console.log("le pseudo du user connecté est : "+userFound.pseudo);
                 console.log("l'id du user connecté est : "+userFound.id);
                 console.log("le mail du user est : "+userFound.email);
                 console.log("le password du user est : "+userFound.password);

                //user homepage
                this.gotoUserEntrance()
              }
              //error return
              else{
                this.connectionService.alreadyExists = true;
                this.connectionService.mismatchedPassword = true
                return;
              }

          }

          else{
            console.log("le user n'a pas été trouvé !")
            this.alreadyTried = true;
            this.connectionService.alreadyExists = false;
            return;
          }

        }
    );
  }
  /*
  retrieveUserMovies(){
    this.userService.retrieveUserMovies();
  }*/


addToWatchList(movie:MovieFullInformations){
      //this.userService.
      this.movieService.addMovieToUserListWithoutDataBase(movie);
      //check if updated worked for user-connection
      for(let i=0;i<this.movieService.userMoviesList.length;i++){
        console.log("les films présents : "+this.movieService.userMoviesList[i].imdbID);
      }

}

/**

  this method enables to prepare a list with all the movies registered by the user in database.

   */
  retrieveUserMovies(){
    console.log("on récupère les films du user "+this.userService.userAccount.pseudo+" "+this.userService.userAccount.id);
    this.movieService.findAllMoviesFromUserList(this.userService.userAccount.id).subscribe((movies) => {
      /*
         on s'assure que le film a bien été trouvé
         avant de l'affecter à this.movieFull*/
          /* on donne à movieFull
          les infos du film qui nous intéresse*/
              if (movies != null){
                console.log('les de l\'utilisateur films ont bien été trouvé');
                for(let movie of movies){
                  console.log("on ajoute "+movie.title);
                  console.log("le imdb est : "+movie.imdbID);
                  this.movieService.getMovieComplete(movie.imdbID).subscribe((movieComplete) => {
                    console.log("film trouvé avec l'API : "+movieComplete.imdbID+ " "+movieComplete.Title);
                    this.addToWatchList(movieComplete);
                  });
                }
              }

          }
        );
  }

gotoUserEntrance() {
    //prepare user's movie list
    this.retrieveUserMovies();
    this.router.navigate(['/user/entrance']);
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }

  ngOnInit(){}
}

