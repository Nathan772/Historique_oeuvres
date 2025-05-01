import { Component } from '@angular/core';
import { MovieServiceService } from '../../movies/movie_service/movie-service.service';
import { UserService } from '../user_service/user-service.service';
import { User } from '../user';
import {ConnectionServiceService} from '../../connection/connection-service.service';
import { DefaultUserComponent} from '../../default_user_menu/default-user.component';

@Component({
  selector: 'app-movie-menu-user',
  templateUrl: './movie-menu-user.component.html',
  styleUrl: './movie-menu-user.component.scss'
})
export class MovieMenuUserComponent {
  userService:UserService;
  movieService:MovieServiceService;
  connectionService:ConnectionServiceService;


    /* l'initialisation
      du service dans le constructeur
      est indispensable*/
      constructor(movieService: MovieServiceService, userService:UserService, connectionService:ConnectionServiceService) {
        this.movieService = movieService;
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
