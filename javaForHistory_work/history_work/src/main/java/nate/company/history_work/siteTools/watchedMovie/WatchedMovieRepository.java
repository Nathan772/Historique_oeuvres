package nate.company.history_work.siteTools.watchedMovie;

import jakarta.transaction.Transactional;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchedMovieRepository extends CrudRepository<WatchedMovie,Long> {
    public Optional<WatchedMovie> getById(long id);

    @Query("SELECT wm FROM WatchedMovie wm WHERE wm.watcher = :user and wm.movie = :movieWatched")
    public List<WatchedMovie> findByUserAndMovie(User user, Movie movieWatched);

    //and movie = :movieWatched.id
    //, Movie movieWatched


    @Query("DELETE FROM WatchedMovie wm WHERE wm.id = :idWatchMovie")
    @Modifying
    @Transactional
    public void removeById(long idWatchMovie);
}
