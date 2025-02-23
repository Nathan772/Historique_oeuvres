package nate.company.history_work.controller.movie;

import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import nate.company.history_work.siteTools.watch_read.WatchMovie;
import nate.company.history_work.siteTools.watch_read.WatchMovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Unit test of the {@link MovieController} class.
 *
 * @author Dylan DE JESUS
 * @author Nathan BILINGI
 * @see MovieController
 */
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired        // Injects dependencies
    private MockMvc mockMvc;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private WatchMovieRepository watchMovieRepository;

    @MockBean
    private UserRepository userRepository; // Without this Mock, the test can't be launched

    /**
     * Checks that all the movies saved by a user are all retrieved.
     *
     * @throws Exception if the perform method of the mockMVC throws it
     */
    @Test
    public void shouldGetUserMovies() throws Exception {
        var firstMovie = new Movie(1L, "Le Comte de Monte-Cristo", 2024, "ID_1", "Matthieu Delaporte");
        var secondMovie = new Movie(2L, "Barbie", 2023, "ID_2", "Greta Gerwig");
        var firstWatchMovie = new WatchMovie(1L, 1L, "À regarder plus tard");
        var secondWatchMovie = new WatchMovie(1L, 2L, "À regarder plus tard");

        when(watchMovieRepository.findAll()).thenReturn(List.of(firstWatchMovie, secondWatchMovie));
        when(movieRepository.findById(1L)).thenReturn(Optional.of(firstMovie));
        when(movieRepository.findById(2L)).thenReturn(Optional.of(secondMovie));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/movie?id=1")) // Forgetting the '/' character at the beginning makes the  method throw an exception
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value("Le Comte de Monte-Cristo"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].imdbID").value("ID_1"))
                .andExpect(jsonPath("$[1].title").value("Barbie"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].imdbID").value("ID_2"));
    }

    /**
     * Checks that an empty list is returned when a valid user doesn't have
     * any movie in his bookmark.
     *
     * @throws Exception if the perform method of the mockMVC throws it
     */
    @Test
    public void shouldReturnNoneMovie() throws Exception {
        when(watchMovieRepository.findAll()).thenReturn(List.of());
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/movie?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    /**
     * Checks that a the retrieval of a movie of a uer that doesn't exist in the database should
     * return a Not Found code.
     *
     * TODO : To pass this test, the method of the controller must be implemented in a different way that checks the user id exist
     *
     * @throws Exception if the perform method of the mockMVC throws it
     */
    @Test
    public void shouldGetError() throws Exception {
        when(watchMovieRepository.findAll()).thenReturn(List.of());
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/movie?id=1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Checks the save of a movie that already exists in the database.
     *
     * @throws Exception if the perform method of the mockMVC throws it
     */
    @Test
    public void shouldAddMovie() throws Exception {
        String jsonRequest =
                """
                {
                    "user" : 
                    {
                          "id" : 1,
                          "pseudo" : "leoMessi",
                          "email" : "lm@sfr.fr",
                          "password" : "bestFootballPlayer",
                          "category" : "average"
                    },
                    "movie" : 
                    {
                           "id" : 2,
                           "title" : "Barbie",
                           "year" : 2023,
                           "imdbID" : "ID_2",
                           "director" : "Greta Gerwig"
                    }
                }
                """;

        var firstMovie = new Movie(1L, "Le Comte de Monte-Cristo", 2024, "ID_1", "Matthieu Delaporte");
        var secondMovie = new Movie(2L, "Barbie", 2023, "ID_2", "Greta Gerwig");
        var watchMovie = new WatchMovie(1L, 2L, "À regarder plus tard");

        // TODO : Must modify these lines after the changes of the addMovie method
        when(movieRepository.findAll()).thenReturn(List.of(firstMovie, secondMovie));
        when(watchMovieRepository.save(watchMovie)).thenReturn(watchMovie);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/movie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("Barbie"));
    }


    /**
     * Checks the save of a movie that does not exist in the database.
     *
     * @throws Exception if the perform method of the mockMVC throws it
     */
    @Test
    public void shouldAddMovie2() throws Exception {
        String jsonRequest =
                """
                {
                    "user" : 
                    {
                          "id" : 1,
                          "pseudo" : "leoMessi",
                          "email" : "lm@sfr.fr",
                          "password" : "bestFootballPlayer",
                          "category" : "average"
                    },
                    "movie" : 
                    {
                           "id" : 1,
                           "title" : "Barbie",
                           "year" : 2023,
                           "imdbID" : "ID_1",
                           "director" : "Greta Gerwig"
                    }
                }
                """;
        var movie = new Movie(1L, "Barbie", 2023, "ID_1", "Greta Gerwig");
        var watchMovie = new WatchMovie(1L, 2L, "À regarder plus tard");

        // TODO : Must modify these lines after the changes of the addMovie method
        when(movieRepository.findAll()).thenReturn(List.of());  // Error there, due to the method of the controller calls this repository instruction at two different times
        when(movieRepository.save(movie)).thenReturn(movie);
        when(watchMovieRepository.save(watchMovie)).thenReturn(watchMovie);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/movie/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Barbie"));
    }

    /**
     * Checks that a movie can be deleted.
     *
     * @throws Exception if the perform method of the mockMVC throws it
     */
    @Test
    public void shouldDeleteMovieFromList() throws Exception {
        var movie = new Movie(1L, "The Batman", 1984, "Note", "Nolan");
        var watchMovie = new WatchMovie(1L, 1L, "À regarder plus tard");

        when(movieRepository.findAll()).thenReturn(List.of(movie));
        when(watchMovieRepository.findAll()).thenReturn(List.of(watchMovie));

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/movie/remove/1/Note"))
                .andExpect(status().isNoContent());
    }
}