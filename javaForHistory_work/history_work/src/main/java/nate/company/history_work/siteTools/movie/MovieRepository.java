package nate.company.history_work.siteTools.movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CRUD which means Create, READ, UPDATE, DELETE
 */
@Repository
public interface MovieRepository extends CrudRepository<Movie,Long> {
    public Movie save(Movie movie);
    public Optional<Movie> getById(long id);
    public Optional<Movie> getByimdbID(String imdbID);
}
