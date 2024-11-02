import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user_service/user-service.service';
import { User } from '../user';
import { ConnectionServiceService } from "../../connection/connection-service.service";

@Component({
  selector: 'app-user-entrance',
  templateUrl: './user-entrance.component.html',
  styleUrl: './user-entrance.component.scss'
})
export class UserEntranceComponent {

  router:Router;
  userService:UserService;
  connectionService:ConnectionServiceService;
  /*
  le user service du constructeur permet d'être utilisé
  pour utiliser le user offert.
  */
  constructor(routerParam: Router,service: UserService, connectionService:ConnectionServiceService ) {
    //the user is not connected redirection...
    if(connectionService.isConnected == false){
      console.log("l'utilisateur n'est pas connecté")
      this.redirectionToConnectionPage();
    }
    console.log("le user est déjà connecté ");
    //retrieve data from the component that initiate the connection
    this.router = routerParam;
    this.userService = service;
    this.connectionService = connectionService;
    this.connectionService.isConnected = connectionService.isConnected;
    this.connectionService.alreadyExists = false;
    this.connectionService.mismatchedPassword = false;
  }

 /**
   this method starts a disconnection
   */
  /*disconnect(){
    this.connectionService.isConnected = false;
    this.connectionService.alreadyExists = false
    this.connectionService.mismatchedPassword = false;
    this.userService.userAccount = {id:0,pseudo:"", password:"", email:""};
    this.redirectionToConnectionPage();
  }*/

  redirectionToConnectionPage() {
    console.log("you're not connected : go back to login page");
     //not connected
     this.connectionService.isConnected = false;
     this.connectionService.alreadyExists = false
     this.connectionService.mismatchedPassword = false;
     //no user
     //this.userService.userAccount = null;

     //welcome page for connexion or inscription
     this.router.navigate(['/']);

  }

  /*
  this method send to the welcome page for a connected user
  */
  gotoUserEntrance() {

    this.router.navigate(['/userEntrance']);
  }

  ngOnInit(){}


}
