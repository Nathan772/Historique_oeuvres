/*

Cette class permet de gérer les intéractions avec java
en appelant des méthodes liées à java.
Les méthodes sont celles de UserController.java


*/
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { User } from '../user';
import { Observable, of } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import {ConnectionServiceService} from '../../connection/connection-service.service';

@Injectable({
  providedIn:'root'})
export class UserService {

  router:Router;
  private usersUrl: string;
  private registerUrl: string;
  public userAccount:User = {id:0,pseudo:"",email:"",password:"",category:"average"};
  private connectUrl:string;
  private userExistsUrl:string;

  constructor(private http: HttpClient, routerParam: Router) {
    //usersUrl va permettre de faire le lien avec le backend
    //l'@ 8080 est une @ du backend
    this.usersUrl = 'http://localhost:8080/users';
    this.registerUrl = 'http://localhost:8080/register';
    this.connectUrl = 'http://localhost:8080/connect';
    this.userExistsUrl = 'http://localhost:8080/userSearch';
    this.router = routerParam;
  }


 /**
  retrieve a user in the data base
  */
  /* deprecated, can use sensitive data
  public findUser(user:User): Observable<User> {
    //let headers = new Headers();
    headers.append('Content-Type','application/json');
    headers.append()
    //let params = new URLSearchParams();
    //params.append("",)
    //let options = user ?
    { params: new HttpParams().set('userName', user) } : {};
    //const params = new HttpParams().set('','');
    return this.http.get<User>(this.userExistsUrl, { params: this.ToHttpParams(user)});
  }*/






/**
   this method starts a disconnection
   */
  disconnect(connectionService:ConnectionServiceService){
    connectionService.isConnected = false;
    connectionService.alreadyExists = false
    connectionService.mismatchedPassword = false;
    this.userAccount = {id:0,pseudo:"", password:"", email:"",category:"average"};
    this.redirectionToConnectionPage(connectionService);
  }

redirectionToConnectionPage(connectionService:ConnectionServiceService) {
    console.log("you're not connected : go back to login page");
     //not connected
     connectionService.isConnected = false;
     connectionService.alreadyExists = false
     connectionService.mismatchedPassword = false;
     //no user
     //this.userService.userAccount = null;

     //welcome page for connexion or inscription
     this.router.navigate(['/']);

  }




  /**
    retrieve a user in the data base
    */
  public findUser(user:User): Observable<User> {
    return this.http.post<User>(this.userExistsUrl,user);
  }

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





  /* récupère les users que l'on souhaite prendre depuis
  la base.
  Elle est liée à getUsers, de UserController
   */
  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

/* sauvegarde en base un utilisateur choisit
la méthode save réalise une requête avec
l'@ "'http://localhost:8080/users'"
du constructor.
Elle est liée à "addUser"  du
UserController.java

*/
  public save(user: User) {
    console.log("On sauvegarde un nouvel utilisateur : "+user);
    return this.http.post<User>(this.usersUrl, user);
  }


/**
 cette méthode permet la suppresion d'un user.
 Elle est liée à
 removeUser de UserController.java.
 la méthode delete réalise une requête avec l'@ :

http://localhost:8080/user
 */
  public delete(user: User) {
    console.log("On supprime un nouvel utilisateur : "+user);
    console.log("le user a pour id : "+user.id)
    //use the url associated deleteMapping of UserController
    return this.http.delete(this.usersUrl+"/delete/"+user.id);
    //censé marcher : "http://localhost:8080/users/delete/302"
  } /*
     set data for the user, into the service
     */
  /* useless deprecated
  public setUser(userChosen:Observable<User>){
     return userChosen.subscribe(
      userFound => {
        this.userAccount = userFound;
      console.log("après ou avant ?")
      return true;
      }
    );
  }*/

    /* this method do a redirection to home page (mostly because the user is not connected or disconnected
      */
    /*redirectionToConnectionPage() {
        console.log("you're not connected : go back to login page");
         //not connected
         this.connectionService.isConnected = false;
         this.connectionService.alreadyExists = false
         this.connectionService.mismatchedPassword = false;
         //no user
         //this.userService.userAccount = null;

         //welcome page for connexion or inscription
         this.router.navigate(['/']);

      }*/

    /*
    this method update user connection status and data if it's necessary
    */
    public async prepareConnection(connectionService:ConnectionServiceService){
      //the user is not connected redirection to connection page
          //reload user from the same session (BEGIN)
          if(connectionService.isConnected == false && sessionStorage.getItem('userPseudo') == null){
            console.log("l'utilisateur n'est pas connecté")
            this.redirectionToConnectionPage(connectionService);
          }

          //the user is already connect, reload their data
          if(sessionStorage.getItem('userPseudo') !== null && connectionService.isConnected == false){
            console.log("la valeur du userPseudo "+sessionStorage.getItem('userPseudo'));
            //let userPseudo = JSON.parse(sessionStorage.getItem('userPseudo') || '{}');
            let userPseudo = sessionStorage.getItem('userPseudo') || "noUserFound";
            let userSimple:User = {
                  id:0,
                  pseudo:userPseudo,
                  email:"none@gmail.com",
                  password:"none",
                  category:"average"
              }


            /* await handle asychronous response
            for the retrieve of the user
            source : https://stackoverflow.com/questions/63136965/how-to-wait-for-function-with-subscribe-to-finish-in-angular-8
            */
            this.userAccount = await this.findUser(userSimple).toPromise() || userSimple;
            //let isReady = this.setUser(userDataBase);


            console.log("le user récupéré vaut maintenant: "+this.userAccount.pseudo+" et a pour id "+this.userAccount.id);
            //set connection "on";
            connectionService.isConnected = true;
          }
          console.log("le user est déjà connecté ");


          connectionService = connectionService;
          connectionService.isConnected = connectionService.isConnected;
          connectionService.alreadyExists = false;
          connectionService.mismatchedPassword = false;
          //reload user from the same session (END)
    }


}