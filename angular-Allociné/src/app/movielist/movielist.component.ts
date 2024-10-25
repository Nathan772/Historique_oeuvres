import { Component, Input, OnInit } from '@angular/core';
import { MovieShortInformations } from '../model/models';
import { MovieCardComponent } from '../movie-card/movie-card.component';

@Component({
  selector: 'app-movielist',
  templateUrl: './movielist.component.html',
  styleUrls: ['./movielist.component.css'],
})
export class MovielistComponent implements OnInit {
  /* le mot "Input" est indispensable
  pour permettre au père de communiquer avec
   ce champ pour lui trasmettre des infos
   (voir le html du père)
   */
  @Input()
  listFilm: MovieShortInformations[];

  constructor() {}

  /* c'est dans cette partie que le fils peut 
  utiliser les éléments du père*/
  ngOnInit(): void {}

  displayMovies(films: MovieShortInformations[]) {}
}
