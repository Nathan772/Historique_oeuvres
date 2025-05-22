package nate.company.history_work.siteTools.anime;

import nate.company.history_work.siteTools.movie.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimeShortRepository extends CrudRepository<AnimeShort,Long>  {
    public Optional<AnimeShort> getById(long id);
    public Optional<AnimeShort> getByimdbID(String imdbID);
}
