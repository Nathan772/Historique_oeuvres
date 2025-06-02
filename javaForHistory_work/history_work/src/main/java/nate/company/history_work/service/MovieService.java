package nate.company.history_work.service;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

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
        System.out.println("on save le movie en db:  "+movie);
        //save only if the movie is absent
        if(movieRepository.getByimdbID(movie.getImdbID()).isEmpty()) {
            movieRepository.save(movie);
            return;
        }
       nate.company.history_work.logger.LoggerInfo.LOGGER.log(Level.INFO, "the movie is already present cannot add it twice to the db");

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
