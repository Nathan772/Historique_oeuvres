import { Component } from '@angular/core';
import { MovieServiceService } from '../../movies/movie_service/movie-service.service';
import { UserService } from '../user_service/user-service.service';
import { User } from '../user';
import {ConnectionServiceService} from '../../connection/connection-service.service';

@Component({
  selector: 'app-movie-menu-user',
  templateUrl: './movie-menu-user.component.html',
  styleUrl: './movie-menu-user.component.scss'
})
export class MovieMenuUserComponent {
  userService:UserService;
  movieService:MovieServiceService;
  connectionService:ConnectionServiceService;

  ngOnInit() {}


    /* l'initialisation
      du service dans le constructeur
      est indispensable*/
      constructor(movieService: MovieServiceService, userService:UserService, connectionService:ConnectionServiceService) {
        this.movieService = movieService;
        this.userService = userService;
        this.connectionService = connectionService;
      }

}
