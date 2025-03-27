package src.main.java.nate.company.history_work.controller.movie;

import nate.company.history_work.entity.EmailDetails;
import nate.company.history_work.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.main.java.nate.company.history_work.siteTools.movie.MovieRepository;

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
@RestController
@CrossOrigin("*")
// permet de résoudre le problème de "No Access-control-allow-origin" cors policy error
public class MovieController {
     /*
   necessary constructor for REST API
     */
    //standard constructors

    /**
     * Repository that stores movies.
     */
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
    public MovieController(MovieRepository movieRepository){
        Objects.requireNonNull(movieRepository);
        this.movieRepository = movieRepository;
    }

    /**
     * This method retrieves all the users from the database
     * (linked to "findAll" from user.service)
     *
     * @param userId id of the user
     * @return the list of movie possessed by the user
     */
    //@RequestMapping("/users")
    //deprecated overpowerful watch UserService
//    @GetMapping("user/movie")
//    public List<Movie> getUserMovies(@RequestParam(name="id") long userId){
//        var list = new ArrayList<Movie>();
//         for(var watchMovie:watchMovieRepository.findAll()){
//            if(watchMovie.getIdUser() == userId){
//                var movie = movieRepository.findById(watchMovie.getIdMovie());
//                //add movies to the list of movies possessed by the user
//                if(movie.isPresent()) {
//                    LOGGER.log(Level.INFO,"Le film "+movie+" a été ajouté à la liste des films du user");
//                    //transform movie in order to get a field Id
//                    list.add(new Movie(movie.get().getId(),movie.get().getTitle(),movie.get().getYearOfRelease(),movie.get().getImdbID(),movie.get().getDirector()));
//                }
//            }
//        }
//         return List.copyOf(list);
//    }





    /**
     * The "add movie" add a new movie into the data base.
     *
     * @param wrapperUserMovie the wrapper that contains the user and the movie.
     *
     * @return the retrieved movie
     */
    //Requestbody can be used only once,
    //thereby you use a wrapper class
    // request functional on Postman
    //deprecated overpowerful
//    @PostMapping("/user/movie/add")
//    public Movie addMovie(@RequestBody WrapperUserMovie wrapperUserMovie){
//        LOGGER.log(Level.INFO, " on ajoute le film : "+wrapperUserMovie.getMovie());
//        //save the movie in database
//        Movie movieSaved;
//        //check if movie already exists first :
//        for(var existingMovie:movieRepository.findAll()){
//            //this movie is already in the database
//            if(existingMovie.getImdbID().equals(wrapperUserMovie.getMovie().getImdbID())){
//                movieSaved = existingMovie;
//
//                //save the link movie - user, in database
//                var watchMovie = new WatchMovie(wrapperUserMovie.getUser().getId(), movieSaved.getId(), "à regarder plus tard");
//                LOGGER.log(Level.INFO, " le movie à regarder a pour date de dernière update : "+watchMovie.getLastUpdate());
//                //System.out.println(" le movie à regarder a pour date de de dernière update : "+watchMovie.getLastUpdate());
//                watchMovieRepository.save(watchMovie);
//
//                //System.out.println("film sauvegardé pour le user...");
//                LOGGER.log(Level.INFO, " film sauvegardé pour le user :"+wrapperUserMovie.getUser().getPseudo());
//
//                return wrapperUserMovie.getMovie();
//            }
//        }
//
//        //the movie is not in the database yet
//        var movieToAdd = wrapperUserMovie.getMovie();
//        LOGGER.log(Level.INFO,"adding the movie date of release data : "+movieToAdd.getYearOfRelease()+" name : "+movieToAdd.getTitle());
//        movieRepository.save(movieToAdd);
//
//
//        //retrieve its actual ID
//        for(var movie:movieRepository.findAll()){
//            if(movie.getImdbID().equals(movieToAdd.getImdbID())){
//                movieSaved = movie;
//                System.out.println(movieSaved.getId());
//                //save the association : movie-user in a table of the data base
//                var watchMovie = new WatchMovie(wrapperUserMovie.getUser().getId(), movieSaved.getId(), "à regarder plus tard");
//                watchMovieRepository.save(watchMovie);
//                LOGGER.log(Level.INFO, "film sauvegardé pour le user...");
//                return wrapperUserMovie.getMovie();
//            }
//        }
//        LOGGER.log(Level.WARNING, "it is not supposed to happen : movie could not be registered");
//        return null;
//    }


}







