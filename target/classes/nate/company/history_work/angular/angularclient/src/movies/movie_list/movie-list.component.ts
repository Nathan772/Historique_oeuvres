import { Component, Input, OnInit } from '@angular/core';
import { MovieShortInformations } from '../movie_models/movie_models';
import { Movie} from '../movie_models/movie_models';
import { MovieCardComponent } from '../movie_card/movie-card.component';
import { MovieServiceService } from '../movie_service/movie-service.service';
import { UserService } from '../../user/user_service/user-service.service';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css'],
})
export class MovieListComponent implements OnInit {
  /* le mot "Input" est indispensable
  pour permettre au père de communiquer avec
   ce champ pour lui trasmettre des infos
   (voir le html du père)
   */
  @Input()
  listMovies: MovieShortInformations[];

  movieService: MovieServiceService;
  userService : UserService;
  currentPage:number;
  numberPerPage:number;


  constructor(userService:UserService,movieService:MovieServiceService) {
    /*
    necessary to prevent from 'not init'
    error
    */
    this.listMovies = [];
    this.movieService = movieService;
    this.userService = userService;
    this.currentPage = 0;
    this.numberPerPage = 3;


    /* deprecated
    this.movieService.findAllMoviesFromUserList(userService.userAccount.pseudo, userService.userAccount.password).subscribe(
          list => this.listMovies = list);
          */
  }

  /* c'est dans cette partie que le fils peut
  utiliser les éléments du père*/
  ngOnInit(): void {}

  /**
   this method returns some movies by a number of pages*/
  MoviesByPage() {
    let moviesPerPage:MovieShortInformations[] = []
    for(let i = this.currentPage*this.numberPerPage;i<(this.currentPage+1)*this.numberPerPage;i++){
      moviesPerPage.push(this.listMovies[i])
    }
    return moviesPerPage;
  }

/**

go to the next page of movies
   */
  increasesPages() {
    if((this.currentPage+1)*this.numberPerPage > this.listMovies.length){
      this.currentPage+=1;
    }
  }

/**

go to the previous page of movies
   */
  decreasesPages() {
    if((this.currentPage+1)*this.numberPerPage < this.listMovies.length){
      this.currentPage-=1;
    }
  }


  displayMovies(films: MovieShortInformations[]) {}
}
