package nate.company.history_work.siteTools.watchedMovie;

import nate.company.history_work.siteTools.movie.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchedMovieRepository extends CrudRepository<WatchedMovie,Long> {
    public Optional<WatchedMovie> getById(long id);
}
