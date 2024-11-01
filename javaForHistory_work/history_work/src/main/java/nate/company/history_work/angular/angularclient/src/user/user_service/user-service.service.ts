/*

Cette class permet de gérer les intéractions avec java
en appelant des méthodes liées à java.
Les méthodes sont celles de UserController.java


*/
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { User } from '../user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn:'root'})
export class UserService {

  private usersUrl: string;
  private registerUrl: string;
  public userAccount:User = {id:0,pseudo:"",email:"",password:""};
  private connectUrl:string;
  private userExistsUrl:string;

  constructor(private http: HttpClient) {
    //usersUrl va permettre de faire le lien avec le backend
    //l'@ 8080 est une @ du backend
    this.usersUrl = 'http://localhost:8080/users';
    this.registerUrl = 'http://localhost:8080/register';
    this.connectUrl = 'http://localhost:8080/connect';
    this.userExistsUrl = 'http://localhost:8080/userSearch';
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
  }
}
