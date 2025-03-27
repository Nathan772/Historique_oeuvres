import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user_service/user-service.service';
import { ConnectionServiceService } from "../../connection/connection-service.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})

/*
certains message d'erreurs
peuvent n'avoir aucun rapport avec
la vraie cause de l'erreur,
c'est donc au user de trouver en tatonnant
et en cherchant ce qui est inhabituel.
J'ai ainsi eux un msg
qui parlait d'une accolade,
alors que les causes de l'erreur étaient
la présence du mot "var" avant le nom
de la variable et aussi un
constructeur de service qui n'utilisait pas
le mot "this" + le paramètre
service qui était privé
*/
export class UserListComponent implements OnInit {

  users: User[];
  userService:UserService;
  router:Router;
  connectionService:ConnectionServiceService;


  constructor(routerParam: Router, service: UserService,  connectionService:ConnectionServiceService) {
    this.users = [];
    this.userService = service;
    this.router = routerParam;
    this.connectionService = connectionService
    this.userService.prepareConnection(this.connectionService);
    this.userService.findAll().subscribe(users=>
      {
      if(users != null){
        this.users = users;
       }
     }
    );

  }

/*

this method removes a user
based on their information.
*/

  removeUser(user:User){
    //this.userService.delete(user);
    console.log("on remove l'utilisateur : "+user);
    /*le subscribe semble obligatoire
    pour permettre le bon déroulement de la suppression
    */
    this.userService.delete(user).subscribe(data => {
      console.log(user+" a été supprimé ! ");
      })
    //recharge la page après suppression
    window.location.reload();
  }

  gotoUserList(){
    this.router.navigate(['/users']);
  }

/*
init the list that contains all the users
in the database.
*/
  ngOnInit() {
    //stocke les users récupérés
    // dans this.users
    this.userService.findAll().subscribe(data => {
              this.users = data;
            });

  }
}
