package nate.company.history_work.service;

import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;

    }

    public void saveMovie(Movie movie){
        movieRepository.save(movie);
    }

    public Optional<Movie> getMovieById(long idMovie){
        var movie = movieRepository.getById(idMovie);
        if(movie.isEmpty()){
            return Optional.empty();
        }
        return movie;
    }

    public Optional<Movie> getMovieByImdb(String imdbId){
        Objects.requireNonNull(imdbId);
        var movie = movieRepository.getByimdbID(imdbId);
        if(movie.isEmpty()){
            return Optional.empty();
        }
        return movie;
    }
}
