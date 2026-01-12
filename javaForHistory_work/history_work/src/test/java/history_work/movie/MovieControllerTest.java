package history_work.movie;

import nate.company.history_work.Application;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.person.Person;
import nate.company.history_work.siteTools.person.PersonRepository;
import nate.company.history_work.siteTools.status.VisualArtStatus;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




///**
// * Unit test of the {@link MovieController} class.
// *
// * @author Dylan DE JESUS
// * @author Nathan BILINGI
// * @see MovieController
// */

//@DataJpaTest
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired        // Injects dependencies
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private WatchedMovieRepository watchMovieRepository;

    @Autowired
    private UserRepository userRepository; // Without this Mock, the test can't be launched

    @Autowired
    private PersonRepository personRepository;

    //functional
    @Test
    public void testShouldGetEmptyUserMovies() throws Exception {
        var director1 = new Person("Mathieu","Delaporte");
        var firstMovie = new Movie("Le Comte de Monte-Cristo", 2024, "Imdb_1", director1, "https://fr.web.img6.acsta.net/c_300_300/img/29/eb/29eb8341475fdb0b19b1d7b995b70e17.jpg");
        var director2 = new Person("Greta","Gerwig");
        var secondMovie = new Movie("Barbie", 2023, "Imdb_2", director2, "https://www.reddit.com/media?url=https%3A%2F%2Fpreview.redd.it%2Fofficial-poster-for-greta-gerwigs-barbie-v0-w1hmtffyjs7b1.jpg%3Fauto%3Dwebp%26s%3D0597bd09ee35dba16a16109995f18fdc0928c12f");
        var user1 = new User("jeanPP", "jeanpp@gmail.com", "666666");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        user1.setPassword(encoder.encode(user1.getPassword()));
        userRepository.save(user1);
        //connect user
        mockMvc.perform(MockMvcRequestBuilders.get("/validAuthentication").param("pseudo", "jeanPP").param("password","666666")).andExpect(status().isOk());

        var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/movie").param("pseudo","jeanPP").param("password", "666666")).andReturn();
        var mvcResponse = mvcResult.getResponse().getContentAsString();
        System.out.println("les films du user : "+mvcResponse);
        // Forgetting the '/' character at the beginning makes the  method throw an exception.andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
    }

//    /**
//     * Checks that all the movies saved by a user are all retrieved.
//     *
//     * @throws Exception if the perform method of the mockMVC throws it
//     */
    @Test
    public void shouldGetUserMovies() throws Exception {
        var director1 = new Person("Mathieu","Delaporte");
        var director2 = new Person("Greta","Gerwig");
        var secondMovie = new Movie("Barbie", 2023, "Imdb_2", director2, "https://www.reddit.com/media?url=https%3A%2F%2Fpreview.redd.it%2Fofficial-poster-for-greta-gerwigs-barbie-v0-w1hmtffyjs7b1.jpg%3Fauto%3Dwebp%26s%3D0597bd09ee35dba16a16109995f18fdc0928c12f");
        var user1 = new User("jeanPP", "jeanpp@gmail.com", "666666");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        user1.setPassword(encoder.encode(user1.getPassword()));
        user1 = userRepository.save(user1);

        //save necessary data (and alone data) firstly
        director1 = personRepository.save(director1);

        var firstMovie = new Movie("Le Comte de Monte-Cristo", 2024, "Imdb_1", director1, "https://fr.web.img6.acsta.net/c_300_300/img/29/eb/29eb8341475fdb0b19b1d7b995b70e17.jpg");
        firstMovie = movieRepository.save(firstMovie);

        //update data

        director1.addMovieDirected(firstMovie);
        firstMovie.setDirector(director1);
        firstMovie= movieRepository.save(firstMovie);

        //you don't need to save director1 again, the save with firstMovie propagate to dir1

        director2 = personRepository.save(director2);
        director2.addMovieDirected(secondMovie);
        secondMovie =movieRepository.save(secondMovie);




        //connect user
        mockMvc.perform(MockMvcRequestBuilders.get("/validAuthentication").param("pseudo", "jeanPP").param("password","666666")).andExpect(status().isOk());


        //save watched movie from the user
        var watchedMovie = new WatchedMovie(user1, firstMovie, VisualArtStatus.WATCHLATER,0);

         watchMovieRepository.save(watchedMovie);
        user1.addWatchedMovie(watchedMovie);
        watchMovieRepository.save(watchedMovie);
        var watchedMovie2 = new WatchedMovie(user1, secondMovie, VisualArtStatus.WATCHLATER,0);
        user1.addWatchedMovie(watchedMovie2);
        watchMovieRepository.save(watchedMovie2);





        //get user's movies

        mockMvc.perform(MockMvcRequestBuilders.get("/user/movie").param("pseudo","jeanPP").param("password", "666666")) // Forgetting the '/' character at the beginning makes the  method throw an exception
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[1].movie.title").value("Le Comte de Monte-Cristo"))
                .andExpect(jsonPath("$[1].movie.id").value(1L))
                .andExpect(jsonPath("$[1].movie.imdbID").value("Imdb_1"))
                .andExpect(jsonPath("$[0].movie.title").value("Barbie"))
                .andExpect(jsonPath("$[0].movie.id").value(2L))
                .andExpect(jsonPath("$[0].movie.imdbID").value("Imdb_2"));
    }

    /**
     * Checks that an empty list is returned when a valid user doesn't have
     * any movie in his bookmark.
     *
     * @throws Exception if the perform method of the mockMVC throws it
     */
//    @Test
//    public void shouldReturnNoneMovie() throws Exception {
//        when(watchMovieRepository.findAll()).thenReturn(List.of());
//        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/user/movie?id=1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isEmpty());
//    }

    /**
     * Checks that a the retrieval of a movie of a uer that doesn't exist in the database should
     * return a Not Found code.
     *
     * TODO : To pass this test, the method of the controller must be implemented in a different way that checks the user id exist
     *
     * @throws Exception if the perform method of the mockMVC throws it
     */
//    @Test
//    public void shouldGetError() throws Exception {
//        when(watchMovieRepository.findAll()).thenReturn(List.of());
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/user/movie?id=1"))
//                .andExpect(status().isNotFound());
//    }

//    /**
//     * Checks the save of a movie that already exists in the database.
//     *
//     * @throws Exception if the perform method of the mockMVC throws it
//     */
//    @Test
//    public void shouldAddMovie() throws Exception {
//        String jsonRequest =
//                """
//                {
//                    "user" :
//                    {
//                          "id" : 1,
//                          "pseudo" : "leoMessi",
//                          "email" : "lm@sfr.fr",
//                          "password" : "bestFootballPlayer",
//                          "category" : "average"
//                    },
//                    "movie" :
//                    {
//                           "id" : 2,
//                           "title" : "Barbie",
//                           "year" : 2023,
//                           "imdbID" : "ID_2",
//                           "director" : "Greta Gerwig"
//                    }
//                }
//                """;
//
//        var firstMovie = new Movie(1L, "Le Comte de Monte-Cristo", 2024, "ID_1", "Matthieu Delaporte");
//        var secondMovie = new Movie(2L, "Barbie", 2023, "ID_2", "Greta Gerwig");
//        var watchMovie = new WatchMovie(1L, 2L, "À regarder plus tard");
//
//        // TODO : Must modify these lines after the changes of the addMovie method
//        when(movieRepository.findAll()).thenReturn(List.of(firstMovie, secondMovie));
//        when(watchMovieRepository.save(watchMovie)).thenReturn(watchMovie);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/user/movie/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonRequest))
//                .andExpect(jsonPath("$.id").value(2L))
//                .andExpect(jsonPath("$.title").value("Barbie"));
//    }
//
//
//    /**
//     * Checks the save of a movie that does not exist in the database.
//     *
//     * @throws Exception if the perform method of the mockMVC throws it
//     */
//    @Test
//    public void shouldAddMovie2() throws Exception {
//        String jsonRequest =
//                """
//                {
//                    "user" :
//                    {
//                          "id" : 1,
//                          "pseudo" : "leoMessi",
//                          "email" : "lm@sfr.fr",
//                          "password" : "bestFootballPlayer",
//                          "category" : "average"
//                    },
//                    "movie" :
//                    {
//                           "id" : 1,
//                           "title" : "Barbie",
//                           "year" : 2023,
//                           "imdbID" : "ID_1",
//                           "director" : "Greta Gerwig"
//                    }
//                }
//                """;
//        var movie = new Movie(1L, "Barbie", 2023, "ID_1", "Greta Gerwig");
//        var watchMovie = new WatchMovie(1L, 2L, "À regarder plus tard");
//
//        // TODO : Must modify these lines after the changes of the addMovie method
//        when(movieRepository.findAll()).thenReturn(List.of());  // Error there, due to the method of the controller calls this repository instruction at two different times
//        when(movieRepository.save(movie)).thenReturn(movie);
//        when(watchMovieRepository.save(watchMovie)).thenReturn(watchMovie);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/user/movie/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.title").value("Barbie"));
//    }
//
//    /**
//     * Checks that a movie can be deleted.
//     *
//     * @throws Exception if the perform method of the mockMVC throws it
//     */
//    @Test
//    public void shouldDeleteMovieFromList() throws Exception {
//        var movie = new Movie(1L, "The Batman", 1984, "Note", "Nolan");
//        var watchMovie = new WatchMovie(1L, 1L, "À regarder plus tard");
//
//        when(movieRepository.findAll()).thenReturn(List.of(movie));
//        when(watchMovieRepository.findAll()).thenReturn(List.of(watchMovie));
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/user/movie/remove/1/Note"))
//                .andExpect(status().isNoContent());
//    }
}