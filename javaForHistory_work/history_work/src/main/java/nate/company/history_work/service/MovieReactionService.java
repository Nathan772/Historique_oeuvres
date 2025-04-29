package nate.company.history_work.service;

import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.reaction.MovieReaction;
import nate.company.history_work.siteTools.reaction.MovieReactionRepository;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Component
public class MovieReactionService {
    @Autowired
    private final MovieReactionRepository movieReactionRepository;

    @Autowired
    public MovieReactionService(MovieReactionRepository movieReactionRepository){
        this.movieReactionRepository = movieReactionRepository;
    }

    public void save(MovieReaction movieReaction){
        Objects.requireNonNull(movieReaction);
        movieReactionRepository.save(movieReaction);
    }

    public Optional<MovieReaction> findByUserAndMovie(User user, Movie movie){
        /*
        find the watched movie data
         */
        //movie
        return movieReactionRepository.findByUserAndMovie(user, movie).stream().findFirst();
    }
}
