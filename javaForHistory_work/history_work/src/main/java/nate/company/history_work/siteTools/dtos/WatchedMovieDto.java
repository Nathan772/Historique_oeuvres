package nate.company.history_work.siteTools.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.timeHandler.TimeConverter;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.status.VisualArtStatus;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;

import static nate.company.history_work.siteTools.timeHandler.TimeConverter.fromSecondToOnlyTimeObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WatchedMovieDto {
    private final long id;
    //Overkill, and it causes issues because movie doesn't refers to watched moviedto
    //@JsonBackReference
    private final MovieDto movie;
    /*
    prevent from "1001 depth" infinite reference
     */
    @JsonBackReference
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
        this.movieStatus = VisualArtStatus.WATCHLATER.ordinal();
    }

    @JsonCreator
    public WatchedMovieDto(long id, MovieDto movie, UserDto watcherDto,
                           int seconds,
                           int minutes,
                           int hours,
                           VisualArtStatus movieStatus){
        this.id = id;
        this.movie = movie;
        this.watcherDto = watcherDto;
        this.time = new TimeConverter.OnlyTime(seconds,minutes,hours);
        this.movieStatus = movieStatus.ordinal();
    }
    /*private WatchedMovieDto(WatchedMovie watchedMovie){
        this.id = watchedMovie.getId();
        this.movie = new MovieDto(watchedMovie.getMovie());
        this.watcherDto = new UserDto(watchedMovie.getWatcher(), true);
        var actualTime = fromSecondToOnlyTimeObject(watchedMovie.getTimeAsLong());
        this.time = new TimeConverter.OnlyTime(actualTime.getSeconds(), actualTime.getMinutes(),actualTime.getHours());
        this.movieStatus = watchedMovie.getMovieStatus().ordinal();
    }*/

    /*
    by default, ignore field to prevent from infinite loop
     */
    public WatchedMovieDto(WatchedMovie watchedMovie){
        this(watchedMovie, true);
    }

    public WatchedMovieDto(WatchedMovie watchedMovie, boolean ignore){
        this.id = watchedMovie.getId();
        //ignore if necessary to prevent from nested (infinite) loop
        if(!ignore) {
            this.watcherDto = new UserDto(watchedMovie.getWatcher(), true);
            this.movie = new MovieDto(watchedMovie.getMovie());
        }
        else {
            watcherDto = null;
            this.movie = new MovieDto(watchedMovie.getMovie());
        }
        var actualTime = fromSecondToOnlyTimeObject(watchedMovie.getTimeAsLong());
        this.time = new TimeConverter.OnlyTime(actualTime.getSeconds(), actualTime.getMinutes(),actualTime.getHours());
        this.movieStatus = watchedMovie.getArtStatus().ordinal();
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

    public TimeConverter.OnlyTime getTime() {
        return time;
    }
}
