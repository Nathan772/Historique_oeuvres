import { Pipe, PipeTransform } from '@angular/core';
import { Movie, watchedMovie} from '../movies/movie_models/movie_models';
@Pipe({
  name: 'orderBy',
})
export class OrderByPipe implements PipeTransform {

  transform(value:watchedMovie[], order: "asc" | "desc" = "asc"): watchedMovie[] {
      return value.sort((a, b) => {
        if (order === "asc") {
          return a.movie.title.localeCompare(b.movie.title);
        } else if (order === "desc") {
          return a.movie.title.localeCompare(b.movie.title);
        }
        return 0;
      });
    }

}
