package nate.company.youtube_converter;

/* import proposé au clique sur les différentes annotations en rouge

 */
import nate.company.youtube_converter.siteTools.User;
import nate.company.youtube_converter.siteTools.UserRepository;
import nate.company.youtube_converter.siteTools.Video;
import nate.company.youtube_converter.siteTools.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.stream.Stream;

/*
créé moi-même copie du code de la page baledung.com :
ajoutés manuellement
 */
@SpringBootApplication
/*
nécessaire pourgit  résoudre :
Parameter 0 of method init in nate.company.youtube_converter.Application
required a bean of type 'nate.company.youtube_converter.siteTools.UserRepository' that could not be found.
 */
@ComponentScan({"nate/company/youtube_converter"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, VideoRepository videoRepository) {
        return args -> {
            /*pas nécessaire sauf si on veut tester l'ajout en brut
            sans passer par l'application Web
            Stream.of("JohnD", "JulieB", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = new User(name, name.toLowerCase() + "@domain.com");
                userRepository.save(user);
            });
             */

            //même chose, mais pour les vidéos

           /* Stream.of("https://www.youtube.com/watch?v=fAZUbQRf6DI",
                    "https://www.youtube.com/watch?v=e6brGv5af3w").forEach(link -> {
                Video video = new Video(link);
                videoRepository.save(video);
            });*/

            System.out.println(" les données en base de données : ");
            userRepository.findAll().forEach(System.out::println);
            videoRepository.findAll().forEach(System.out::println);
        };
    }
}
