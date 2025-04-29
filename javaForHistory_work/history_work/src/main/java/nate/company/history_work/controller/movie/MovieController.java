package nate.company.history_work.controller.movie;

import nate.company.history_work.siteTools.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Acts like a REST controller that manages the requests about movies.
 *
 * @author Nathan BILINGI
 * @author Dylan DE JESUS
 */

@CrossOrigin("*")
// permet de résoudre le problème de "No Access-control-allow-origin" cors policy error
@RestController
public class MovieController {
     /*
   necessary constructor for REST API
     */
    //standard constructors

    /**
     * Repository that stores movies.
     */
    @Autowired
    private final MovieRepository movieRepository;

    /**
     * Repository that stores movies watched by a user.
     * deprecated
     */
//    private final WatchMovieRepository watchMovieRepository;

    /**
     * Constructs (by myself) filled with a parameter of type UserRepo. Spring will generate
     * the injection of the argument itself when the constructor is called by a third part.
     *
     * @param movieRepository the repository that stores all the movies
     *
     */
    @Autowired
    public MovieController(MovieRepository movieRepository){
        Objects.requireNonNull(movieRepository);
        this.movieRepository = movieRepository;
    }


}







