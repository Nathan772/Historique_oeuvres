import { Pipe, PipeTransform } from '@angular/core';
import { Movie, watchedMovie} from '../movies/movie_models/movie_models';
import { Anime, watchedAnime } from '../anime/anime-models/anime-models';
@Pipe({
  name: 'orderBy',
})
export class OrderByPipe implements PipeTransform {

/**
(values)
function for orderby (order a list):
(order)
type of list with its elements
order
choice for the order of the element (ascendent, or descedant)


*/
  transform(values:watchedMovie[], order: "asc" | "desc" = "asc"): watchedMovie[] {
      return values.sort((a, b) => {
        if (order === "asc") {
          //compare status status
          if(a.movieStatus !=b.movieStatus){
            return a.movieStatus-b.movieStatus
            }
          //compare movies names if status are alike
          else {
            if(a.movie.title <= b.movie.title){
              return -1;
            }
          }
        }
        else if (order === "desc") {
          //status first the same meaning
           if(a.movieStatus !=b.movieStatus){
                      return a.movieStatus-b.movieStatus
           }
          //compare movies names if status are alike but in reverse order
                    else {
                      if(a.movie.title >= b.movie.title){
                        return -1;
                      }
                    }
        }
        return 0;
      });
    }

  transformAnime(values:watchedAnime[], order: "asc" | "desc" = "asc"): watchedAnime[] {
        return values.sort((a, b) => {
          if (order === "asc") {
            //compare status status
            if(a.animeStatus !=b.animeStatus){
              return a.animeStatus-b.animeStatus
              }
            //compare movies names if status are alike
            else {
              if(a.anime.Title <= b.anime.Title){
                return -1;
              }
            }
          }
          else if (order === "desc") {
            //status first the same meaning
             if(a.animeStatus !=b.animeStatus){
                        return a.animeStatus-b.animeStatus
             }
            //compare movies names if status are alike but in reverse order
                      else {
                        if(a.anime.Title >= b.anime.Title){
                          return -1;
                        }
                      }
          }
          return 0;
        });
      }

}
