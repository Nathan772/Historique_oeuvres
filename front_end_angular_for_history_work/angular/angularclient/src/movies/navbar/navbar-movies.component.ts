import { Component, EventEmitter, OnInit, Output } from '@angular/core';
@Component({
  selector: 'app-navbar-movies',
  templateUrl: './navbar-movies.component.html',
  styleUrls: ['./navbar-movies.component.css'],
})
export class NavbarMoviesComponent implements OnInit {
  /* le string qui contiendra la chaine entrée par
  le user*/
  searchValue: string = '';

  constructor() {}
  /*on va utiliser cet émeteur pour émettre un string
  qui correspond à la chaine trouvée, lorsque
  l'utilisateur fera une recherche.
  L'émetteur est possédé par celui qui émet l'information
  c'est à dire celui qui sait qu'il y a eu un
  événement et qui va avertir l'autre composant*/
  @Output()
  searchActivate = new EventEmitter<string>();

  searchLaunched() {
    if (this.searchValue === '') return;
    /* on émet un événement de type string associé
    à la chaine entrée par l'utilisateur lors de sa recherche*/
    this.searchActivate.emit(this.searchValue);
  }

  ngOnInit() {}
}
