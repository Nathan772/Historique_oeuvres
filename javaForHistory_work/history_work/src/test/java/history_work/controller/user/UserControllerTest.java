package history_work.controller.user;


//@WebMvcTest(UserController.class)
///**
// * Unit test for the UserController class.
// */

import nate.company.history_work.Application;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

//both annotation are necessary to enable the use of repositories but they cause bug in pipeline
@SpringBootTest(classes= Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private MovieRepository movieRepository;
//
//    @Autowired
//    private WatchMovieRepository watchMovieRepository;

    @Test
    public void assertTrueWorks() throws Exception {
       Assertions.assertTrue(true);
    }

    /**
     * Checks that all the user are retrieved.
     *
     * @throws Exception if the mockMVC could not perform
     */
//    @Test
//    public void shouldGetAllUsersFromDB() throws Exception {
//        var user1 = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
//        var user2 = new User("rafNadal", "rn@sfr.fr", "besTennisPlayer");
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
//        user1.setPassword(encoder.encode(user1.getPassword()));
//        user2.setPassword(encoder.encode(user2.getPassword()));
//        user1 = userRepository.save(user1);
//        user2 = userRepository.save(user2);
//
//
//        var users = userRepository.findAll();
//        System.out.println("le contenu de users est : "+users);
//        System.out.println("l'état de user1 est : "+user1);
//        System.out.println("l'état de user2 est : "+user1);
//        Assertions.assertTrue(users.contains(user1));
//        Assertions.assertTrue(users.contains(user2));
//
//
//        //mockMvc.perform(MockMvcRequestBuilders.get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$[0].pseudo").value("leoMessi"));
//    }
}
//
    /**
     * Ensures that the user can be retrieved thanks to its information in the database.
     *
     * @throws Exception if the mockMVC could not perform.
     */
//    @Test
//    public void shouldGetIdentifier() throws Exception {
//        String jsonRequest =
//                """
//                {
//                  "id" : 0,
//                  "pseudo" : "leoMessi",
//                  "email" : "lm@sfr.fr",
//                  "password" : "bestFootballPlayer",
//                  "category" : "average"
//                }
//                """;
//        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
//        when(userRepository.findAll()).thenReturn(List.of(user));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/userSearch")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(0L))
//                .andExpect(jsonPath("$.pseudo").value("leoMessi"))
//                .andExpect(jsonPath("$.email").value("lm@sfr.fr"))
//                .andExpect(jsonPath("$.category").value("average"))
//                .andExpect(jsonPath("$.password").value("bestFootballPlayer"));
//    }
//
//
//    /**
//     * Checks that the identifier can be retrieved even with pseudo or email doesn't match
//     * the User in the database.
//     *
//     * @throws Exception if the mockMVC could not perform.
//     */
//    @Test
//    public void shouldGetIdentifier2() throws Exception {
//        String jsonRequest =
//                """
//                {
//                  "id" : 0,
//                  "pseudo" : "crisRonaldo",
//                  "email" : "lm@sfr.fr",
//                  "password" : "bestFootballPlayer",
//                  "category" : "average"
//                }
//                """;
//        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
//        when(userRepository.findAll()).thenReturn(List.of(user));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/userSearch")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(0L))
//                .andExpect(jsonPath("$.pseudo").value("leoMessi"))
//                .andExpect(jsonPath("$.email").value("lm@sfr.fr"))
//                .andExpect(jsonPath("$.category").value("average"))
//                .andExpect(jsonPath("$.password").value("bestFootballPlayer"));
//    }
//
//    /**
//     * Checks that a non-existing user (in the database) can't be retrieved.
//     *
//     * @throws Exception if the mockMVC could not perform
//     */
//    @Test
//    public void shouldNotGetIdentifier() throws Exception {
//        String jsonRequest =
//                """
//                {
//                  "id" : 0,
//                  "pseudo" : "crisRonaldo",
//                  "email" : "cr7@sfr.fr",
//                  "password" : "bestFootballPlayer",
//                  "category" : "average"
//                }
//                """;
//        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
//        when(userRepository.findAll()).thenReturn(List.of(user));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/userSearch")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isNotFound());
//    }
//
//    /**
//     * Checks that a user can be added.
//     *
//     * @throws Exception if the mockMVC could not perform
//     */
//    @Test
//    public void shouldAddUser() throws Exception {
//        String jsonRequest =
//                """
//                {
//                  "id" : 0,
//                  "pseudo" : "leoMessi",
//                  "email" : "lm@sfr.fr",
//                  "password" : "bestFootballPlayer",
//                  "category" : "average"
//                }
//                """;
//        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
//        when(userRepository.save(user)).thenReturn(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonRequest))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(0L))
//                .andExpect(jsonPath("$.pseudo").value("leoMessi"))
//                .andExpect(jsonPath("$.email").value("lm@sfr.fr"))
//                .andExpect(jsonPath("$.category").value("average"))
//                .andExpect(jsonPath("$.password").value("bestFootballPlayer"));
//    }
//
//    /**
//     * Checks that the user is successfully deleted.
//     *
//     * @throws Exception if the mockMVC could not perform
//     */
//    @Test
//    public void shouldRemoveUser() throws Exception {
//        var user = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1"))
//                .andExpect(status().isOk());
//    }
//
//    /**
//     * Checks that the deletion returns "not found" code for non-existing user.
//     *
//     * @throws Exception if the mockMVC could not perform
//     */
//    @Test
//    public void shouldReturnNotFound() throws Exception {
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/1"))
//                .andExpect(status().isNotFound());
//    }
//}