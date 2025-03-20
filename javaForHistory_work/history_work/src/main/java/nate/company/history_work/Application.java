package nate.company.history_work;

/* import proposé au clique sur les différentes annotations en rouge

 */
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.stream.Stream;

/**
 * This is the starting point of the application.
 */
/*
créé moi-même copie du code de la page baledung.com :
ajoutés manuellement
 */
@SpringBootApplication
/*
nécessaire pour git  résoudre :
Parameter 0 of method init in nate.company.youtube_converter.Application

required a bean of type 'nate.company.youtube_converter.siteTools.UserRepository' that could not be found.
 */
@ComponentScan({"nate/company/history_work"})
public class Application {

    /**
     * Starts the application.
     *
     * @param args the arguments on the command line
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, MovieRepository movieRepository) {
        return args -> {
            /*pas nécessaire sauf si on veut tester l'ajout en brut
            sans passer par l'application Web*/
            /*Stream.of("JohnD", "JulieB", "Jennifer", "Helen", "Rachel").forEach(name -> {
                userRepository.save(new User(name, name+"@gmail.com", "666666"));
            });


            movieRepository.save(new Movie());
            watchMovieRepository.save(new WatchMovie(1,1,"à regarder plus tard"));
            */
            //même chose, mais pour les vidéos

            System.out.println(" les données en base de données : ");
//            userRepository.findAll().forEach(System.out::println);
//            movieRepository.findAll().forEach(System.out::println);
            //watchMovieRepository.findAll().forEach(System.out::println);
        };
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        /*
        it loads .env file
         */
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        configurer.setLocation(new FileSystemResource("javaForHistory_work/history_work/src/.env"));
        return configurer;
    }
}
