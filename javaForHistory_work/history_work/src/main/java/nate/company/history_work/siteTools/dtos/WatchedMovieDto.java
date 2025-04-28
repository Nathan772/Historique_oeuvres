package nate.company.history_work.siteTools.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedMovie.MovieStatus;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WatchedMovieDto {
    private final long id;
    private final MovieDto movie;
    private final UserDto watcherDto;
    private final long timeAsLong;
    private final MovieStatus movieStatus;

    public WatchedMovieDto(){
        this.id = 0;
        this.movie = new MovieDto(new Movie());
        this.watcherDto = new UserDto(new User());
        this.timeAsLong = 0;
        this.movieStatus = MovieStatus.WATCHLATER;
    }

    @JsonCreator
    public WatchedMovieDto(long id, MovieDto movie, UserDto watcherDto, long timeAsLong, MovieStatus movieStatus){
        this.id = id;
        this.movie = movie;
        this.watcherDto = watcherDto;
        this.timeAsLong =  timeAsLong;
        this.movieStatus = movieStatus;
    }
    public WatchedMovieDto(WatchedMovie watchedMovie){
        this.id = watchedMovie.getId();
        this.movie = new MovieDto(watchedMovie.getMovie());
        this.watcherDto = new UserDto(watchedMovie.getWatcher(), true);
        this.timeAsLong =  watchedMovie.getTimeAsLong();
        this.movieStatus = watchedMovie.getMovieStatus();
    }

    /*
    getter are necessary for json creator
     */
    public long getId() {
        return id;
    }

    public UserDto getWatcherDto() {
        return watcherDto;
    }

    public MovieDto getMovie() {
        return movie;
    }

    public MovieStatus getMovieStatus() {
        return movieStatus;
    }

    public long getTimeAsLong() {
        return timeAsLong;
    }
}
