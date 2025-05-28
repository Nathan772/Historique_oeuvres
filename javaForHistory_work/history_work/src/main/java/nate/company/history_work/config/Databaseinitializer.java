package nate.company.history_work.config;

import jakarta.transaction.Transactional;
import nate.company.history_work.service.MovieService;
import nate.company.history_work.service.PersonService;
import nate.company.history_work.service.UserService;
import nate.company.history_work.service.WatchMovieService;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.person.Person;
import nate.company.history_work.siteTools.person.PersonRepository;
import nate.company.history_work.siteTools.reaction.MovieReactionRepository;
import nate.company.history_work.siteTools.reaction.ReactionRepository;
import nate.company.history_work.siteTools.status.VisualArtStatus;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Databaseinitializer implements CommandLineRunner {


    @Autowired
    UserService userService;
    @Autowired
    MovieService movieService;
    @Autowired
    WatchMovieService watchMovieService;
    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Database initialization started");
        /*
        this part are test in order to check if everything works properly for save
         */
//        Person person = new Person("Jean","Test");
//        personService.savePerson(person);
//        //retrieve with the id initiate
//        person = personService.findByFirstNameAndLastName(person.getFirstName(),person.getLastName()).get();
//        Movie movie1 = new Movie("Le retour du movie",1999,"adggce",person, "abc.png");
//        movieService.saveMovie(movie1);
//        personService.savePerson(person);
//        movie1 = movieService.getMovieByImdb(movie1.getImdbID()).get();
//        User user = new User("JeanValj","jeanvalj@gmail.com","666666");
//        userService.saveUser(user);
//        user = userService.getUserByPseudo(user.getPseudo()).get();
//        WatchedMovie watchedMovie = new WatchedMovie(user,movie1, VisualArtStatus.WATCHING,525);
//        watchMovieService.saveWatchMovie(watchedMovie);
//        movieService.saveMovie(movie1);
        //watchedMovie.setMovie(movie1);
        //works until here
        //perceive movie as detached

        //need the watched movie saved in db to be saved
        //userService.saveUser(user);
        //necessary to get an actual id for a watchedMovie

//        watchedMovie = watchMovieService.findByUserAndMovie(user,movie1).get();



//        watchedMovie = watchMovieService.findByUserAndMovie(user, movie1).get();
//        user.addWatchedMovie(watchedMovie, movie1);
//        userService.saveUser(user);
        System.out.println(personRepository.findAll());
        System.out.println(movieRepository.findAll());
        System.out.println(userRepository.findAll());

    }


}
