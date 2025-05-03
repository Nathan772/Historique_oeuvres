import { Component } from '@angular/core';
import { Pipe, PipeTransform } from "@angular/core";
import { Movie, watchedMovie} from '../movies/movie_models/movie_models';

@Pipe({
  name: "lowerCase",
})

export class LowerCasePipeComponent {
    transform(value: watchedMovie, ...args: unknown[]): unknown {
      return value.movie.title.toLowerCase();
    }

}
