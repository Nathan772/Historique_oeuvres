import { Component } from '@angular/core';

import { UserService } from '../user_service/user-service.service';
import { User } from '../user';
import {ConnectionServiceService} from '../../connection/connection-service.service';
import { DefaultUserComponent} from '../../default_user_menu/default-user.component';
import { AnimeServiceService } from '../../anime/anime_service/anime-service.service';
@Component({
  selector: 'app-anime-menu-user',
  templateUrl: './anime-menu-user.component.html',
  styleUrl: './anime-menu-user.component.scss'
})
export class AnimeMenuUserComponent {
  userService:UserService;
  animeService:AnimeServiceService;
  connectionService:ConnectionServiceService;


    /* l'initialisation
      du service dans le constructeur
      est indispensable*/
      constructor(animeService: AnimeServiceService, userService:UserService, connectionService:ConnectionServiceService) {
        this.animeService = animeService;
        this.userService = userService;
        this.connectionService = connectionService;
        console.log("on passe par le constructeur de movie menu user")
        this.userService.prepareConnection(this.connectionService);
        console.log("les données ont bien été récupérées, le user a pour pseudo :"+this.userService.userAccount.pseudo);
         //retrieve data from the component that initiate the connection
         //useless :this.router = routerParam;
      }

    ngOnInit() {}
}
