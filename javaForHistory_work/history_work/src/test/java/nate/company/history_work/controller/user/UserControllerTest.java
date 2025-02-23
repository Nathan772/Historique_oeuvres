package nate.company.history_work.controller.user;

import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
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


@WebMvcTest(UserController.class)
/**
 * Unit test for the UserController class.
 */
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MovieRepository movieRepository;

    @MockBean
    private WatchMovieRepository watchMovieRepository;

    /**
     * Checks that all the user are retrieved.
     *
     * @throws Exception if the mockMVC could not perform
     */
    @Test
    public void shouldGetAllUsers() throws Exception {
        var user1 = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
        var user2 = new User("rafNadal", "rn@sfr.fr", "besTennisPlayer");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pseudo").value("leoMessi"));
    }

    /**
     * Ensures that the user can be retrieved thanks to its information in the database.
     *
     * @throws Exception if the mockMVC could not perform.
     */
    @Test
    public void shouldGetIdentifier() throws Exception {
        String jsonRequest =
                """
                {
                  "id" : 0,
                  "pseudo" : "leoMessi",
                  "email" : "lm@sfr.fr",
                  "password" : "bestFootballPlayer",
                  "category" : "average"
                }
                """;
        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
        when(userRepository.findAll()).thenReturn(List.of(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/userSearch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0L))
                .andExpect(jsonPath("$.pseudo").value("leoMessi"))
                .andExpect(jsonPath("$.email").value("lm@sfr.fr"))
                .andExpect(jsonPath("$.category").value("average"))
                .andExpect(jsonPath("$.password").value("bestFootballPlayer"));
    }


    /**
     * Checks that the identifier can be retrieved even with pseudo or email doesn't match
     * the User in the database.
     *
     * @throws Exception if the mockMVC could not perform.
     */
    @Test
    public void shouldGetIdentifier2() throws Exception {
        String jsonRequest =
                """
                {
                  "id" : 0,
                  "pseudo" : "crisRonaldo",
                  "email" : "lm@sfr.fr",
                  "password" : "bestFootballPlayer",
                  "category" : "average"
                }
                """;
        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
        when(userRepository.findAll()).thenReturn(List.of(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/userSearch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0L))
                .andExpect(jsonPath("$.pseudo").value("leoMessi"))
                .andExpect(jsonPath("$.email").value("lm@sfr.fr"))
                .andExpect(jsonPath("$.category").value("average"))
                .andExpect(jsonPath("$.password").value("bestFootballPlayer"));
    }

    /**
     * Checks that a non-existing user (in the database) can't be retrieved.
     *
     * @throws Exception if the mockMVC could not perform
     */
    @Test
    public void shouldNotGetIdentifier() throws Exception {
        String jsonRequest =
                """
                {
                  "id" : 0,
                  "pseudo" : "crisRonaldo",
                  "email" : "cr7@sfr.fr",
                  "password" : "bestFootballPlayer",
                  "category" : "average"
                }
                """;
        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
        when(userRepository.findAll()).thenReturn(List.of(user));

        mockMvc.perform(MockMvcRequestBuilders.post("/userSearch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }

    /**
     * Checks that a user can be added.
     *
     * @throws Exception if the mockMVC could not perform
     */
    @Test
    public void shouldAddUser() throws Exception {
        String jsonRequest =
                """
                {
                  "id" : 0,
                  "pseudo" : "leoMessi",
                  "email" : "lm@sfr.fr",
                  "password" : "bestFootballPlayer",
                  "category" : "average"
                }
                """;
        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
        when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(0L))
                .andExpect(jsonPath("$.pseudo").value("leoMessi"))
                .andExpect(jsonPath("$.email").value("lm@sfr.fr"))
                .andExpect(jsonPath("$.category").value("average"))
                .andExpect(jsonPath("$.password").value("bestFootballPlayer"));
    }

    /**
     * Checks that the user is successfully deleted.
     *
     * @throws Exception if the mockMVC could not perform
     */
    @Test
    public void shouldRemoveUser() throws Exception {
        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1"))
                .andExpect(status().isOk());
    }

    /**
     * Checks that the deletion returns "not found" code for non-existing user.
     *
     * @throws Exception if the mockMVC could not perform
     */
    @Test
    public void shouldReturnNotFound() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1"))
                .andExpect(status().isNotFound());
    }
}