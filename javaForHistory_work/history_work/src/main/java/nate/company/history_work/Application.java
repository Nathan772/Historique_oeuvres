/* import proposé au clique sur les différentes annotations en rouge

 */
package nate.company.history_work;
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
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import java.util.stream.Stream;

/**
 * This is the starting point of the application.
 */
/*
créé moi-même copie du code de la page baledung.com :
ajoutés manuellement
 */

/*
nécessaire pour git  résoudre :
Parameter 0 of method init in nate.company.youtube_converter.Application

required a bean of type 'nate.company.youtube_converter.siteTools.UserRepository' that could not be found.
 */
//@ComponentScan({"src/main/java/nate/company/history_work"})
@ComponentScan
@SpringBootApplication
public class Application {

    /**
     * Starts the application.
     *
     * @param args the arguments on the command line
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //UserService userService, MovieService movieService

    @Bean
    CommandLineRunner init(UserService userService, MovieService movieService, UserRepository userRepository, MovieRepository movieRepository,
                           WatchMovieService watchMovieService, WatchedMovieRepository watchedMovieRepository,
                           MovieReactionRepository movieReactionRepository,
                           ReactionRepository reactionRepository,
                           PersonRepository personRepository ,
                           PersonService personService
    ) {
        return args -> {

            /*pas nécessaire sauf si on veut tester l'ajout en brut
            sans passer par l'application Web*/
//            Stream.of("JohnD", "JulieB", "Jennifer", "Helen", "Rachel").forEach(
//                    name -> {
//                userService.saveUser(new User(name, name+"@gmail.com", "666666"));
//            });
//
            //System.out.println("tous les utilisateurs sont actuellement : "+userService.getAllUsers());
//
//            //même chose, mais pour les vidéos
//
//            System.out.println(" les données en base de données : ");
//            System.out.println("Tous les users en bdd :"+userRepository.findAll());
//            System.out.println("tous les films en bdd :"+movieRepository.findAll());
//            System.out.println("tous les films regardés par des users en bdd :"+watchedMovieRepository.findAll());
            //watchMovieRepository.findAll().forEach(System.out::println);


            System.out.println("let's start everything !!!");
        };
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
//        /*
//        it loads .env file
//         */
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
//        configurer.setLocation(new FileSystemResource("javaForHistory_work/history_work/src/.env"));
//        return configurer;
//    }
}
