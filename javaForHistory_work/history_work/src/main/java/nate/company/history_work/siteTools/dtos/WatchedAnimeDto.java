package nate.company.history_work.siteTools.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
//import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.status.VisualArtStatus;
import nate.company.history_work.siteTools.timeHandler.TimeConverter;
import nate.company.history_work.siteTools.user.User;
//import nate.company.history_work.siteTools.watchedAnime.WatchedAnime;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;

import java.util.Objects;

import static nate.company.history_work.siteTools.timeHandler.TimeConverter.fromSecondToOnlyTimeObject;

//public class WatchedAnimeDto {
//    private final long id;
//    //Overkill, and it causes issues because movie doesn't refers to watched moviedto
//    //@JsonBackReference
//    private final AnimeDto anime;
//    /*
//    prevent from "1001 depth" infinite reference
//     */
//    @JsonBackReference
//    private final UserDto watcherDto;
//
//    /*
//    for front end you need
//    actual time rather than
//    a long that represents a duration
//     */
////    private final int minutes;
////    private final int seconds;
////
////    private final int hours;
//
//    private final TimeConverter.OnlyTime time;
//
//    private final int animeStatus;
//
//    public WatchedAnimeDto(){
//        this.id = 0;
//        this.anime = new AnimeDto(new AnimeShort());
//        this.watcherDto = new UserDto(new User());
//        this.time = new TimeConverter.OnlyTime(0,0,0);
//        this.animeStatus = VisualArtStatus.WATCHLATER.ordinal();
//    }
//
//    @JsonCreator
//    public WatchedAnimeDto(long id, AnimeDto animeDto, UserDto watcherDto,
//                           int seconds,
//                           int minutes,
//                           int hours,
//                           VisualArtStatus animeStatusAsEnum){
//        Objects.requireNonNull(animeDto);
//        Objects.requireNonNull(animeStatusAsEnum);
//        Objects.requireNonNull(watcherDto);
//        this.id = id;
//        this.anime = animeDto;
//        this.watcherDto = watcherDto;
//        this.time = new TimeConverter.OnlyTime(seconds,minutes,hours);
//        this.animeStatus = animeStatusAsEnum.ordinal();
//    }
//    /*private WatchedMovieDto(WatchedMovie watchedMovie){
//        this.id = watchedMovie.getId();
//        this.movie = new MovieDto(watchedMovie.getMovie());
//        this.watcherDto = new UserDto(watchedMovie.getWatcher(), true);
//        var actualTime = fromSecondToOnlyTimeObject(watchedMovie.getTimeAsLong());
//        this.time = new TimeConverter.OnlyTime(actualTime.getSeconds(), actualTime.getMinutes(),actualTime.getHours());
//        this.movieStatus = watchedMovie.getMovieStatus().ordinal();
//    }*/
//
//    /*
//    by default, ignore field to prevent from infinite loop
//     */
//    public WatchedAnimeDto(WatchedAnime watchedAnime){
//        this(watchedAnime, true);
//    }
//
//    public WatchedAnimeDto(WatchedAnime watchedAnime, boolean ignore){
//        this.id = watchedAnime.getId();
//        //ignore if necessary to prevent from nested (infinite) loop
//        if(!ignore) {
//            this.watcherDto = new UserDto(watchedAnime.getWatcher(), true);
//            this.anime = new AnimeDto(watchedAnime.getAnimeShort());
//        }
//        else {
//            watcherDto = null;
//            this.anime = new AnimeDto(watchedAnime.getAnimeShort());
//        }
//        var actualTime = fromSecondToOnlyTimeObject(watchedAnime.getTimeAsLong());
//        this.time = new TimeConverter.OnlyTime(actualTime.getSeconds(), actualTime.getMinutes(),actualTime.getHours());
//        this.animeStatus = watchedAnime.getArtStatus().ordinal();
//    }
//}
