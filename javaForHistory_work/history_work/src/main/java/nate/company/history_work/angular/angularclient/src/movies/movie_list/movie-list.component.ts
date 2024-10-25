import { Component, Input, OnInit } from '@angular/core';
import { MovieShortInformations } from '../movie_models/movie_models';
import { MovieCardComponent } from '../movie_card/movie-card.component';

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

  constructor() {
    this.listMovies = [];
  }

  /* c'est dans cette partie que le fils peut
  utiliser les éléments du père*/
  ngOnInit(): void {}

  displayMovies(films: MovieShortInformations[]) {}
}
