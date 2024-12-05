package nate.company.history_work.controller.movie;

import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watch_read.WatchMovie;
import nate.company.history_work.siteTools.watch_read.WatchMovieRepository;
import nate.company.history_work.siteTools.wrapper.WrapperUserMovie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static nate.company.history_work.logger.LoggerInfo.LOGGER;

@RestController

/*
permet de résoudre le problème de
"No Access-control-allow-origin"
cors policy error
 */
@CrossOrigin("*")
public class MovieController {
     /*
   necessary constructor for REST API
     */
    //standard constructors

    private final MovieRepository movieRepository;
    private final WatchMovieRepository watchMovieRepository;

    /*
    constructeur créé par moi-même qui se remplit avec un paramètre de type UserRepo...
    C'est Spring qui gérera lui-même l'ajout/la création de l'argument lors de l'appel
     */
    public MovieController(MovieRepository movieRepository, WatchMovieRepository watchMovieRepository){
        Objects.requireNonNull(movieRepository);
        Objects.requireNonNull(watchMovieRepository);
        this.movieRepository = movieRepository;
        this.watchMovieRepository = watchMovieRepository;
    }

    /**
     * this method retrieves all the users from the database
     * (linked to "findAll" from user.service)
     * @return
     * the list of movie possessed by the user
     */
    //@RequestMapping("/users")
    @GetMapping("user/movie")
    public List<Movie> getUserMovies(@RequestParam(name="id") long userId){
        var list = new ArrayList<Movie>();
         for(var watchMovie:watchMovieRepository.findAll()){
            if(watchMovie.getIdUser() == userId){
                var movie = movieRepository.findById(watchMovie.getIdMovie());
                //add movies to the list of movies possessed by the user
                if(movie.isPresent()) {
                    LOGGER.log(Level.INFO,"Le film "+movie+" a été ajouté à la liste des films du user");
                    //transform movie in order to get a field Id
                    list.add(new Movie(movie.get().getId(),movie.get().getTitle(),movie.get().getYear(),movie.get().getImdbID(),movie.get().getDirector()));
                }
            }
        }
         return List.copyOf(list);
    }


    /**
     * this method retrieve a specific user if exists in database
     * @param userId
     * user's id
     * @param userPseudo
     * pseudo of the user
     * @param email
     * the email from this user
     * @param password
     * the password for this account
     * the user you want to retrieve
     * @return
     * the user searched if found, else null
     */

    /*
    since we use get with parameters,
    we have to retrieve each parameter one by one.
    You must match the parameters
    used in the url with a
    "(name ="paremeter1InTheUrlName") JavaTypeParameter1 param1NameForJava,
    etc..."
    You can check the "right-click + console" to retrieve parameter's names but these are just the names of the fields of the
    class you defined in your angular class file.
    You can prevent your api from explictly saying "RequestParam(name='...')" if the java parameters' method name matches, the name of the
    field of your class (here the class is user). Here it purpesofuly, matches for email and password (for the sake of the example)

     */
    //?id={id}&pseudo={pseudo}&email={email}&password={password}"
    /*@GetMapping("/userSearch")
    @ResponseBody
    public ResponseEntity<?> getUser(@RequestParam(name="id") String userId, @RequestParam(name="pseudo") String userPseudo,
                                     @RequestParam(name="email") String email, @RequestParam(name="password") String password
    ){
        System.out.println("on entre bien dans la méthode getUser de Java");
        var allUsers = userRepository.findAll();
        for(var user:allUsers){
            //user found
            //pseudo already used or email already used
            if(user.getPseudo().equals(userPseudo) || user.getEmail().equals(email) ) {
                System.out.println("le user a bien été trouvé ");
                return ResponseEntity.ok(user);
            }
        }
        user not found (not necessarily an error
        depending on the usage)

        System.out.println("le user a pas été trouvé ");
        return ResponseEntity.ok().build();
    }*/


    /**
     *
     * Retrieve a user based on their pseudo or email.
     * @param user
     * the you user you want to retrieve
     * @return
     * the user object.
     */
    //@RequestMapping("/users")
    //@GetMapping("/user")
    /*public User getUser(@RequestBody User user){

        userRepository.find

    }*/

    /**
     *
     *
     * the "add movie" add a new movie into the data base.
     *
     * @param wrapperUserMovie
     *
     * the wrapper that contains the user and the movie.
     */
    //Requestbody can be used only once,
    //thereby you use a wrapper class
    // request functional on Postman
    @PostMapping("/user/movie/add")
    public Movie addMovie(@RequestBody WrapperUserMovie wrapperUserMovie){
        LOGGER.log(Level.INFO, " on ajoute le film : "+wrapperUserMovie.getMovie());
        //save the movie in database
        Movie movieSaved;
        //check if movie already exists first :
        for(var existingMovie:movieRepository.findAll()){
            //this movie is already in the database
            if(existingMovie.getImdbID().equals(wrapperUserMovie.getMovie().getImdbID())){
                movieSaved = existingMovie;

                //save the link movie - user, in database
                var watchMovie = new WatchMovie(wrapperUserMovie.getUser().getId(), movieSaved.getId(), "à regarder plus tard");
                LOGGER.log(Level.INFO, " le movie à regarder a pour date de dernière update : "+watchMovie.getLastUpdate());
                //System.out.println(" le movie à regarder a pour date de de dernière update : "+watchMovie.getLastUpdate());
                watchMovieRepository.save(watchMovie);

                //System.out.println("film sauvegardé pour le user...");
                LOGGER.log(Level.INFO, " film sauvegardé pour le user :"+wrapperUserMovie.getUser().getPseudo());

                return wrapperUserMovie.getMovie();
            }
        }
        movieRepository.save(wrapperUserMovie.getMovie());


        //retrieve its actual ID
        for(var movie:movieRepository.findAll()){
            if(movie.getImdbID().equals(wrapperUserMovie.getMovie().getImdbID())){
                movieSaved = movie;
                System.out.println(movieSaved.getId());
                //save the association : movie-user in a table of the data base
                var watchMovie = new WatchMovie(wrapperUserMovie.getUser().getId(), movieSaved.getId(), "à regarder plus tard");
                watchMovieRepository.save(watchMovie);
                LOGGER.log(Level.INFO, "film sauvegardé pour le user...");
                return wrapperUserMovie.getMovie();
            }
        }
        LOGGER.log(Level.INFO, "it is not supposed to happen : movie could not be registered");
        return null;
    }

    /**
     *
     * a remove method for an association between a movie and a user.
     *
     * @param id
     * the user who watches the movie
     * @param imdbID
     * the movie watch with its imdbID.
     *
     * the movie and the user who watches this movie.
     *
     * @return
     * a response that precises if everything end up properly.
     */

    @DeleteMapping("/user/movie/remove/{id}/{imdbID}")
    public ResponseEntity<String> removeMovieFromList(@PathVariable String id, @PathVariable String imdbID){

        Movie movieSaved;
        for(var existingMovie:movieRepository.findAll()){
            //this movie is already in the database which is consistent
            if(existingMovie.getImdbID().equals(imdbID)){
                LOGGER.log(Level.INFO, "A movie with the imdb : "+imdbID+" has been found in the database");
                movieSaved = existingMovie;
                var idUserLong = Long.parseLong(id);
                for(var watchMovie:watchMovieRepository.findAll()){
                    //Link between movie and user : found
                    if(watchMovie.getIdMovie() == movieSaved.getId() && watchMovie.getIdUser() == idUserLong){
                        //let's delete all these things
                        //removal succeed
                        watchMovieRepository.delete(watchMovie);
                        LOGGER.log(Level.INFO, "Success : the movie has been correctly removed !!");
                        return ResponseEntity.noContent().build();
                    }
                }

            }
        }
        LOGGER.log(Level.INFO, "Error : the movie that was supposed to be removed hasn't been found !!");
        return ResponseEntity.notFound().build();
    }


    /**
     * a remove method for users.
     * Even if the mapping starts by delete, it starts implicitly by
     * You can try a request with postman to check if it actually works.
     * @param id
     * @return
     */
/*
    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<String> removeUser(@PathVariable String id){
        var userIdLong = Long.parseLong(id);
        //var userIdLong = user.getId();
        System.out.println("on supprime le user avec l'id : "+userIdLong);
        userRepository.deleteById(userIdLong);
         a necessary rreturn to enable removal
        return ResponseEntity.noContent().build();
    }*/
}







