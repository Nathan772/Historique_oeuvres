import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user_service/user-service.service';
import { User } from '../user';
import { ConnectionServiceService } from "../../connection/connection-service.service";
import { ChatbotComponent } from "../../chatbot/chatbot.component";
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
  constructor(routerParam: Router,userService: UserService, connectionService:ConnectionServiceService ) {
    console.log("on passe par le constructeur de user-entrance...");
    this.userService = userService;
    this.connectionService = connectionService;
    this.userService.prepareConnection(this.connectionService);
    console.log("les données ont bien été récupérées, le user a pour pseudo :"+this.userService.userAccount.pseudo);
    //retrieve data from the component that initiate the connection
    this.router = routerParam;
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
