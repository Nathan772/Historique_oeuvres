package nate.company.history_work.siteTools.reaction;

import jakarta.persistence.*;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

/*
a reaction specific for a movie
 */
//@Table(name="movie_reaction_table")
@Entity
@Component
public class MovieReaction extends Reaction {


    /*
    @Id
    @Column(name="id_reaction")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;*/
    @ManyToOne
    @JoinColumn(name="idmovie", nullable=false)
    private Movie movieReacted;

    public MovieReaction(long id, User reactioner, ReactionChoices reaction, Movie movie){
        //set les champs extends
        super(reactioner, reaction);
        this.movieReacted = movie;
        //this.id = id;
    }

    public MovieReaction(User reactioner, ReactionChoices reaction, Movie movie){
        //set les champs extends
        super(reactioner, reaction);
        this.movieReacted = movie;
    }

    public MovieReaction(){
        super();
    }

    public Movie getMovie() {
        return movieReacted;
    }

    public void setMovie(Movie movie) {
        Objects.requireNonNull(movie);
        this.movieReacted = movie;
    }

    /*
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }*/

    public void setMovieReacted(Movie movieReacted) {
        Objects.requireNonNull(movieReacted);
        this.movieReacted = movieReacted;
    }

    public Movie getMovieReacted() {
        return movieReacted;
    }
    /*
    @Override
    public void setReactioner(User reactioner) {
        Objects.requireNonNull(reactioner);
        super.setReactioner(reactioner);
    }

    @Override
    public User getReactioner() {
        return super.getReactioner();
    }*/
}
