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


  /*
  le user service du constructeur permet d'être utilisé
  pour sauvegarder le User offert.

  */
  constructor(routerParam: Router,service: UserService, connectionService:ConnectionServiceService ) {
    this.router = routerParam;
    this.userService =service;
    this.connectionService = connectionService;
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

    this.user = {id:"5", pseudo:"", email:"", password:""};
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
              console.log("le user a été trouvé !")
              //the user exists, starts connexion
              if(userFound.password == this.user.password){
                 //connect the user + retrieve infos
                 this.connectionService.isConnected = true;
                 //update the actual user with their true info (notably the uid)
                 this.userService.userAccount = userFound;

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

gotoUserEntrance() {
    this.router.navigate(['/user/entrance']);
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }

  ngOnInit(){}
}

