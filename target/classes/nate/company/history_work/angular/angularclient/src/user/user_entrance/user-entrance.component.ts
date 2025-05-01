import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user_service/user-service.service';
import { User } from '../user';
  import Typewriter from 'typewriter-effect/dist/core';
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

  //static true means the sentences cannot change
  @ViewChild('typewriterElement', { static: true }) typewriterElement!: ElementRef;

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

//necessary for progressive writing
ngAfterViewInit(): void {
      const typewriter = new Typewriter(this.typewriterElement.nativeElement, {
        //loop: true,
        delay: 75,
      });
      typewriter.erasable = true

      typewriter
         .pauseFor(1000)
        .typeString("Ravi de te voir ")
        .pauseFor(1500)
        //.deleteAll()
        .typeString(this.userService.userAccount.pseudo+ " !")
        .pauseFor(1500)
        .start();

  }

}



