package nate.company.history_work.siteTools.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.timeHandler.TimeConverter;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedMovie.MovieStatus;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;

import static nate.company.history_work.siteTools.timeHandler.TimeConverter.fromSecondToOnlyTimeObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WatchedMovieDto {
    private final long id;
    private final MovieDto movie;
    private final UserDto watcherDto;

    /*
    for front end you need
    actual time rather than
    a long that represents a duration
     */
//    private final int minutes;
//    private final int seconds;
//
//    private final int hours;

    private final TimeConverter.OnlyTime time;

    private final int movieStatus;

    public WatchedMovieDto(){
        this.id = 0;
        this.movie = new MovieDto(new Movie());
        this.watcherDto = new UserDto(new User());
        this.time = new TimeConverter.OnlyTime(0,0,0);
        this.movieStatus = MovieStatus.WATCHLATER.ordinal();
    }

    @JsonCreator
    public WatchedMovieDto(long id, MovieDto movie, UserDto watcherDto,
                           int seconds,
                           int minutes,
                           int hours,
                           MovieStatus movieStatus){
        this.id = id;
        this.movie = movie;
        this.watcherDto = watcherDto;
        this.time = new TimeConverter.OnlyTime(seconds,minutes,hours);
        this.movieStatus = movieStatus.ordinal();
    }
    public WatchedMovieDto(WatchedMovie watchedMovie){
        this.id = watchedMovie.getId();
        this.movie = new MovieDto(watchedMovie.getMovie());
        this.watcherDto = new UserDto(watchedMovie.getWatcher(), true);
        var actualTime = fromSecondToOnlyTimeObject(watchedMovie.getTimeAsLong());
        this.time = new TimeConverter.OnlyTime(actualTime.getSeconds(), actualTime.getMinutes(),actualTime.getHours());
        this.movieStatus = watchedMovie.getMovieStatus().ordinal();
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

    public int getMovieStatus() {
        return movieStatus;
    }

    public TimeConverter.OnlyTime getHours() {
        return time;
    }
}
