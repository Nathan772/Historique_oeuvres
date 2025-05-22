package nate.company.history_work.siteTools.watchedMovie;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import nate.company.history_work.service.WatchMovieService;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.status.VisualArtStatus;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedObject.WatchedObject;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Entity
@Component
@Table(name="watched_movie_table")
public class WatchedMovie extends WatchedObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idWatchedMovie")
    private long id;

    @JoinColumn(name="idmovie")
    @OneToOne(fetch = FetchType.LAZY)
    private Movie movie;


    /*
   necessary for spring init
   (ID is NECESSARY)
    */
    @JsonCreator
    public WatchedMovie(long id, User user, Movie movie, VisualArtStatus status, long timeAsLong){
        super(id,timeAsLong,user, status);
        Objects.requireNonNull(movie);
        this.movie = movie;
    }

    public WatchedMovie(User user, Movie movie, VisualArtStatus status, long timeAsLong){
//        Objects.requireNonNull(user);
//        Objects.requireNonNull(status);
//        Objects.requireNonNull(movie);
//        if(timeAsLong < 0){
//            throw new IllegalArgumentException("WATCHed movie time cannot be lower than 0");
//        }

        //this.watcher = user;
        super(timeAsLong, user, status);
        this.movie = movie;
//        this.movieStatus = status;
//        this.timeAsLong = timeAsLong;
    }

    /*
    necessary for spring
     */
    public WatchedMovie(){
        super();
        this.movie = new Movie();
    }



//    public void setMovieStatus(VisualArtStatus movieStatus) {
//        Objects.requireNonNull(movieStatus);
//        this.movieStatus = movieStatus;
//    }

   // public VisualArtStatus getMovieStatus() {return movieStatus;}

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        Objects.requireNonNull(movie);
        this.movie = movie;
    }

    @Override
    public int hashCode() {
        return movie.hashCode()^getWatcher().hashCode()^Long.hashCode(getId())^Long.hashCode(
                getTimeAsLong()
        )^getArtStatus().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WatchedMovie watchedMovie && watchedMovie.movie.equals(watchedMovie.movie) && watchedMovie.getWatcher()
                .equals(getWatcher());
    }

    @Override
    public String toString() {
        return "le movie : "+movie+" est regardé par : "+getWatcher()+" au temps : "+getTimeAsLong()+" il a pour statut : "+getArtStatus();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
