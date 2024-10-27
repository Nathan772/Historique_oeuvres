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

/*
a simple class to represent movies in database
*/
export interface Movie {
  idmovie:string;
  title: string;
  year: string;
  director: string;
  imdbID: string;
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
