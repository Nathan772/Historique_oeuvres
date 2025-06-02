export interface Rating {
  Source: string;
  Value: string;
}

export interface AnimeFullInformations {
  Title: string;
  Year: string;
  Genre: string;
  Director: string;
  Plot: string;
  Awards: string;
  Poster: string;
  Ratings: Rating[];
  Writer:string;
  Actors:string;
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
export interface AnimeWithStatus {
  anime:AnimeFullInformations;
  status:string;
}
/*
a simple class to represent movies in database
*/
export interface Anime {
  id:string;
  Title: string;
  Year: string;
  imdbID: string;
  Poster:string;
}

/*
export interface MovieShortInformations {

  Title: string;
  Year: string;
  imdbID: string;
  Type: string;
  Poster: string;
}*/

export interface SearchResponse {
  Search: Anime[];
}

export interface watchedAnime {
  anime:Anime;
  animeStatus:watchedAnimeStatus;
  time: timeOnly;
}

export interface reactedAnime {
  anime:Anime;
  reactionChoice:ReactionChoices;
}

export interface timeOnly {
  hours: number;
  minutes: number;
  seconds: number;
}

export enum ReactionChoices {
  Like,
  Dislike,
}


export enum watchedAnimeStatus {
  WATCHING,
  WATCHLATER,
  REWATCH,
}

