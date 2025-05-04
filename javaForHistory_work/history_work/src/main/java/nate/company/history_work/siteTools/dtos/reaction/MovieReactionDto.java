package nate.company.history_work.siteTools.dtos.reaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import nate.company.history_work.siteTools.dtos.MovieDto;
import nate.company.history_work.siteTools.dtos.UserDto;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.reaction.MovieReaction;
import nate.company.history_work.siteTools.reaction.ReactionChoices;
import nate.company.history_work.siteTools.user.User;

import java.util.Objects;

public class MovieReactionDto extends ReactionDto {

    @JsonBackReference
    private MovieDto movieReacted;

    @JsonCreator
    public MovieReactionDto(long id, UserDto reactioner, ReactionChoices reaction, MovieDto movie){
        //set les champs extends
        super(reactioner, reaction);
        this.movieReacted = movie;
        //this.id = id;
    }

    public MovieReactionDto(MovieReaction movieReaction){
        //set les champs extends
        super(movieReaction);
        this.movieReacted = new MovieDto(movieReaction.getMovieReacted());
    }

    public MovieDto getMovie() {
        return movieReacted;
    }

    public void setMovie(MovieDto movie) {
        Objects.requireNonNull(movie);
        this.movieReacted = movie;
    }
    public void setMovieReacted(MovieDto movieReacted) {
        Objects.requireNonNull(movieReacted);
        this.movieReacted = movieReacted;
    }

    public MovieDto getMovieReacted() {
        return movieReacted;
    }
}
