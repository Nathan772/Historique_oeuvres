package nate.company.history_work.siteTools.watchedMovie;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.*;
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
@Data
@AllArgsConstructor
public class WatchedMovie extends WatchedObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idWatchedMovie")
    @Getter
    @Setter
    private long id;

    /*

    WHY MERGE IS NECESSARY :
    https://stackoverflow.com/questions/69890531/why-am-i-obtaining-this-detached-entity-passed-to-persist-when-i-first-retriev

    prevent from double creation of persist in base with the same objct
    contrary to cascade that will propagate the "persist".add

    It solves issues with
    requires a CascadeType.Merge, rather than "ALL" in order to work
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "movie_id", referencedColumnName = "idmovie")
    private Movie movie;


    /*
   necessary for spring init
   (ID is NECESSARY)
    */
    @JsonCreator
    public WatchedMovie(long id, User user, Movie movie, VisualArtStatus status, long timeAsLong){
        super(timeAsLong,user, status);
        Objects.requireNonNull(movie);
        this.movie = movie;
        this.id = id;
    }

    public WatchedMovie(User user, Movie movie, VisualArtStatus status, long timeAsLong){
        super(timeAsLong, user, status);
        System.out.println("les infos du movie qui va être sauvegardé dans watchedmovie: "+movie);
        this.movie = movie;
        this.id = 0;
    }

    /*
    necessary for spring
     */
    public WatchedMovie(){
        super();
        this.movie = new Movie();
        this.id = 0;
    }



//    public void setMovieStatus(VisualArtStatus movieStatus) {
//        Objects.requireNonNull(movieStatus);
//        this.movieStatus = movieStatus;
//    }

   // public VisualArtStatus getMovieStatus() {return movieStatus;}

    public Movie getMovie() {
        return movie;
    }

//    public long getMovieId() {
//        return movie.getId();
//    }

    public void setMovie(Movie movie) {
        Objects.requireNonNull(movie);
        this.movie = movie;
    }

    @Override
    public int hashCode() {
        return movie.hashCode()^getWatcher().hashCode()^Long.hashCode(getId());
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof WatchedMovie watchedMovie && watchedMovie.movie.equals(watchedMovie.movie) && watchedMovie.getWatcher()
                .equals(getWatcher());
    }

    @Override
    public String toString() {
        System.out.println("on affiche le get IsWatched depuis WatchedMovie : "+getMovie().getMovieIsWatchedBy());
        return "le movie : "+movie+" est regardé par : "+getWatcher()+" au temps : "+getTimeAsLong()+" il a pour statut : "+getArtStatus();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
