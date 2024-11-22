export interface Rating {
  Source: string;
  Value: string;
}

export interface SerieFullInformations {
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
export interface SerieWithStatus {
  seire:SerieFullInformations;
  status:string;
}
/*
a simple class to represent movies in database
*/
export interface Serie {
  id:string;
  title: string;
  year: string;
  director: string;
  imdbID: string;
}


export interface SerieShortInformations {

  Title: string;
  Year: string;
  imdbID: string;
  Type: string;
  Poster: string;
}

export interface SearchResponse {
  Search: SerieShortInformations[];
}
