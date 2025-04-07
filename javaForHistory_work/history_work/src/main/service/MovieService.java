package src.main.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import src.main.java.nate.company.history_work.siteTools.movie.Movie;
import src.main.java.nate.company.history_work.siteTools.movie.MovieRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@Component
public class MovieService {
    @Autowired
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
