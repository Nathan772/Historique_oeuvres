import { Injectable } from '@angular/core';
/* pour faire cet import, il faut l'écrire en dur*/
import { HttpClient } from '@angular/common/http';
/* pour faire cet import, il faut l'écrire en dur*/
import { Observable, of } from 'rxjs';
import { NavbarMoviesComponent } from '../navbar/navbar-movies.component';
import {
  MovieFullInformations,
  MovieShortInformations,
  SearchResponse,
} from '../movie_models/movie_models';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root' /* indispensable
  permet de dire où chercher la racine du projet*/,
})
export class MovieServiceService {
  constructor(private HttpClient: HttpClient) {}

  //searchValue:string;

  /* fonction qui prend un argument le titre d'un film
  et qui récupère un observable qui contient les infos du films : titre, année, etc...*/
  getMovieShort(searchValue: string): Observable<MovieShortInformations[]> {
    let myMovieObservable: Observable<MovieShortInformations[]> =
      this.HttpClient.get<SearchResponse>(
        'https://www.omdbapi.com/?apikey=1069fcf1&s=' + searchValue
      ).pipe(map((SearchResponse) => SearchResponse.Search));

    return myMovieObservable;
    //les [...] permettetent de faire
    //une copie du tableau
  }

  /* fonction qui prend un argument l'id d'un film
  et qui récupère un observable qui contient les infos complète du films : titre, année, etc...*/
  getMovieComplete(idFilm: string): Observable<MovieFullInformations> {
    let myMovieObservable: Observable<MovieFullInformations> =
      this.HttpClient.get<MovieFullInformations>(
        'https://www.omdbapi.com/?apikey=1069fcf1&i=' + idFilm
      ).pipe(map((MovieFullInformations) => MovieFullInformations));

    return myMovieObservable;
    //les [...] permettetent de faire
    //une copie du tableau
  }

  public randomDisplay(): void {
    console.log("c'est très réel");
  }
}
