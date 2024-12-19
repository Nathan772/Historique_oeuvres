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
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})


export class UserFormComponent implements OnInit {

  public static rules = {
    pseudoMinLength : 4,
    passwordMinLength : 6,
    emailMinLength : 5
  };


  user: User;
  //copyData{passwordCopy:string};
   //= {id:"5", name:"jean",email:"jean"};
  router:Router;
  userService:UserService;
  copyInfo:copyData;
  //mismatchedPassword:Boolean;
  //alreadyExists:Boolean;
  connectionService:ConnectionServiceService;


  /*
  le user service du constructeur permet d'être utilisé
  pour sauvegarder le User offert.

  */
  constructor(routerParam: Router,service: UserService, connectionService:ConnectionServiceService) {
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

    this.user = {id:0, pseudo:"", email:"", password:"",category:"average"};
    this.copyInfo = {passwordCopy:""};
    /*at the inception
    the user is not connected at all...
    */
    this.connectionService.isConnected = false;
    this.connectionService.alreadyExists = false
    this.connectionService.mismatchedPassword = false;
  }

    /*
     see : https://dev-academy.com/angular-session-storage/
     this method enable to save for a session.
    */
    dataSave(){
        //save user data for long term session
        sessionStorage.setItem('userPseudo', ""+this.userService.userAccount.pseudo);
      }


  /**
   * This getter is useful to access passwordMinLength static
   * field within the HTML context.
   */
  get getStaticPasswordMinLength(){
    return UserFormComponent.rules.passwordMinLength;
  }

  /**
   * This getter is useful to access pseudoMinLength static
   * field within the HTML context.
   */
   get getStaticPseudoMinLength(){
      return UserFormComponent.rules.pseudoMinLength;
   }

   /**
    * This getter is useful to access emailMinLength static
    * field within the HTML context.
    */
    get getStaticEmailMinLength(){
       return UserFormComponent.rules.emailMinLength;
    }


  registerNewUser() {
    console.log("try to register user");
    //check if user exists in data base
    this.userService.findUser(this.user).subscribe(
      user => {
        //The user already exists
        if(user != null){
          this.connectionService.alreadyExists = true;
          //back to the page with error message
          return;
        }
      }
    );

  //mismatched Password
  if(this.copyInfo.passwordCopy != this.user.password){
    this.connectionService.mismatchedPassword=true;
    //back to the page
    //window.location.reload();
    return;
  }


    if(this.connectionService.alreadyExists){
      return;
    }

    //save the new user
    this.userService.save(this.user).subscribe(
      result => {
        //connect the user
        this.connectionService.isConnected = true;
        //update the actual user with their true info (notably the uid)
        this.userService.userAccount = result;
        //no, only for admin now

        //long term session user
        this.dataSave();

        this.gotoUserEntrance()
        });
  }

gotoUserEntrance() {
    this.router.navigate(['/user/entrance']);
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }

  ngOnInit(){}
}

