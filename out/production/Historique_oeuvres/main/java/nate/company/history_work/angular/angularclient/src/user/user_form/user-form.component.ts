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

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})


export class UserFormComponent implements OnInit {


  user: User;
  //copyData{passwordCopy:string};
   //= {id:"5", name:"jean",email:"jean"};
  router:Router;
  userService:UserService;
  copyInfo:copyData;

  /*
  le user service du constructeur permet d'être utilisé
  pour sauvegarder le User offert.

  */
  constructor(routerParam: Router,service: UserService) {
    this.router = routerParam;
    this.userService =service;
    /*nécessaire même si sera remplacé
    car cela correspond aux valeurs par défaut de l'utilisateur
    même si l'id pourrait causer un conflit,
    en pratique ce n'est pas le cas
    car il est aussi automatiquement
    remplacé par la base de donnée
    par le suivant du serial !!
    */

    this.user = {id:"5", pseudo:"", email:"", password:""};
    this.copyInfo = {passwordCopy:""};
  }

  registerNewUser() {
    //check if user exists in data base
    this.userService.findUser(this.user).subscribe(
      user => {
        //The user already exists
        if(user != null)
          return;
      }
    );


    //save the new user
    this.userService.save(this.user).subscribe(
      result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }

  ngOnInit(){}
}

