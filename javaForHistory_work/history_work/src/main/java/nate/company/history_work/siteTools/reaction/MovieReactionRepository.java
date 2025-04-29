package nate.company.history_work.siteTools.reaction;

import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieReactionRepository extends CrudRepository<MovieReaction,Long> {
    public MovieReaction save(MovieReaction movieReaction);

    /*
    retrieves the reacted movie if and only if the user reacted to this
     */
    @Query("SELECT mr FROM MovieReaction mr WHERE mr.reactioner = :user and mr.movieReacted = :movie")
    public List<MovieReaction> findByUserAndMovie(User user, Movie movie);
}
