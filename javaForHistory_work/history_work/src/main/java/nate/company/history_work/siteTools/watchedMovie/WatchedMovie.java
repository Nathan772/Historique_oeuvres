package nate.company.history_work.siteTools.watchedMovie;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Entity
@Component
@Table(name="watched_movie_table")
public class WatchedMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idWatchedMovie")
    private long id;
    @JoinColumn(name="USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User watcher;

    @JoinColumn(name="idmovie")
    @OneToOne(fetch = FetchType.LAZY)
    private Movie movie;

    private MovieStatus movieStatus;

    /*
   necessary for spring init
   (ID is NECESSARY)
    */
    @JsonCreator
    public WatchedMovie(long id, User user, Movie movie, MovieStatus status, long timeAsLong){
        Objects.requireNonNull(user);
        Objects.requireNonNull(status);
        Objects.requireNonNull(movie);
        if(timeAsLong < 0){
            throw new IllegalArgumentException("WATCHed movie time cannot be lower than 0");
        }
        this.id = id;
        this.watcher = user;
        this.movie = movie;
        this.movieStatus = status;
        this.timeAsLong = timeAsLong;
    }
    private long timeAsLong;
    public WatchedMovie(User user, Movie movie, MovieStatus status, long timeAsLong){
        Objects.requireNonNull(user);
        Objects.requireNonNull(status);
        Objects.requireNonNull(movie);
        if(timeAsLong < 0){
            throw new IllegalArgumentException("WATCHed movie time cannot be lower than 0");
        }

        this.watcher = user;
        this.movie = movie;
        this.movieStatus = status;
        this.timeAsLong = timeAsLong;
    }

    /*
    necessary for spring
     */
    public WatchedMovie(){
        this.movie = new Movie();
        this.movieStatus = MovieStatus.WATCHLATER;
        this.timeAsLong = 0;
        this.id = 0;
        this.watcher = new User();
    }


    public void setTimeAsLong(long timeAsLong) {
        if(timeAsLong < 0){
            throw new IllegalArgumentException("time of movie cannot be lower than 0");
        }
        this.timeAsLong = timeAsLong;
    }

    public void setWatcher(User watcher) {
        Objects.requireNonNull(watcher);
        this.watcher = watcher;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        Objects.requireNonNull(movieStatus);
        this.movieStatus = movieStatus;
    }

    public long getTimeAsLong() {
        return timeAsLong;
    }

    public User getWatcher() {
        return watcher;
    }

    public MovieStatus getMovieStatus() {
        return movieStatus;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        Objects.requireNonNull(movie);
        this.movie = movie;
    }

    @Override
    public int hashCode() {
        return movie.hashCode()^watcher.hashCode()^Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WatchedMovie watchedMovie && watchedMovie.movie.equals(watchedMovie.movie) && watchedMovie.watcher.equals(watcher);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "le movie : "+movie+" est regardé par : "+watcher+" au temps : "+timeAsLong+" il a pour statut : "+movieStatus;
    }


}
