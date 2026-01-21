
import { ChatbotComponent } from "../chatbot/chatbot.component";
import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user/user_service/user-service.service';
import { ConnectionServiceService } from "../connection/connection-service.service";
import Typewriter from 'typewriter-effect/dist/core';

@Component({
  selector: 'app-default-user',
  templateUrl: './default-user.component.html',
  styleUrl: './default-user.component.scss'
})
export class DefaultUserComponent {
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
      console.log("on passe par le constructeur de defaul-user-component...");
      this.userService = userService;
      this.connectionService = connectionService;
      this.userService.prepareConnection(this.connectionService);
      //retrieve data from the component that initiate the connection
      this.router = routerParam;
    }
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

  ngOnInit(){
    //retrieve old data if reload of page
    //the user data or default values
    this.userService.userAccount.pseudo = localStorage.getItem('userAccountSavedPseudo') || ""
    this.userService.userAccount.password = localStorage.getItem('userAccountSavedPassword')  || ""
    this.userService.userAccount.category = localStorage.getItem('userAccountSavedCategory') || ""
    this.userService.userAccount.email = localStorage.getItem('userAccountSavedEmail')  || ""
    if(this.userService.userAccount.pseudo === ""){
      this.redirectionToConnectionPage()
      }
  }

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
