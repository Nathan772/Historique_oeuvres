export interface Rating {
  Source: string;
  Value: string;
}

export interface MovieFullInformations {
  Title: string;
  Year: string;
  Genre: string;
  Director: string;
  Plot: string;
  Awards: string;
  Poster: string;
  Ratings: Rating[];
  imdbID: string;
  Type: string;
}

/**
 retrive a movie with its status of
 watching
 ("à regarder plus tard",
   "en cours de visionnage",
   ...)
 */
export interface MovieWithStatus {
  movie:MovieFullInformations;
  status:string;
}
/*
a simple class to represent movies in database
*/
export interface Movie {
  id:string;
  title: string;
  yearOfRelease: string;
  director: string;
  imdbID: string;
  poster:string;
}


export interface MovieShortInformations {

  Title: string;
  Year: string;
  imdbID: string;
  Type: string;
  Poster: string;
}

export interface SearchResponse {
  Search: MovieShortInformations[];
}

export interface watchedMovie {
  movie:Movie;
  movieStatus:watchedMovieStatus;
  time: timeOnly;
}

export interface timeOnly {
  hours: number;
  minutes: number;
  seconds: number;
}

export enum watchedMovieStatus {
  WATCHLATER,
  WATCHING,
  REWATCH,
}
