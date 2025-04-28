package nate.company.history_work.service;

import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;

@Service
public class WatchMovieService {
    private final WatchedMovieRepository watchedMovieRepository;

    @Autowired
    public WatchMovieService(WatchedMovieRepository watchedMovieRepository){
        this.watchedMovieRepository = watchedMovieRepository;

    }

    public void saveWatchMovie(WatchedMovie watchedMovie){
        /*
        save the watched movie data
         */
        watchedMovieRepository.save(watchedMovie);
    }

    public Optional<WatchedMovie> findByUserAndMovie(User user, Movie movie){
        /*
        find the watched movie data
         */
        //movie
        return watchedMovieRepository.findByUserAndMovie(user, movie).stream().findFirst();
    }

    public void removeByIdMovie(long idWatchMovie){
        /*
        remove the watched movie data from data base
         */
        watchedMovieRepository.removeById(idWatchMovie);
    }
}
