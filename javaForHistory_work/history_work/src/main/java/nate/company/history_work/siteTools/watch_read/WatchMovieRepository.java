package nate.company.history_work.siteTools.watch_read;

import nate.company.history_work.siteTools.movie.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CRUD which means Create, READ, UPDATE, DELETE
 */


@Repository
public interface WatchMovieRepository extends CrudRepository<WatchMovie,Long>{
    //public List<Movie> findMoviesByUserId(int userId);
}
