package nate.company.history_work.controller.user;

import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import nate.company.history_work.siteTools.watch_read.WatchMovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
/*@SpringBootTest
@AutoConfigureMockMvc*/
public class UserControllerTest {

  @Autowired        // Injects dependencies
  private MockMvc mockMvc;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private MovieRepository movieRepository;

  @MockBean
  private WatchMovieRepository watchMovieRepository;

  @Test
  public void shouldGetAllUsers() throws Exception {
    var user1 = new User("leoMessi", "lm@sfr.fr", "bestFootballPlayer");
    var user2 = new User("rafNadal", "rn@sfr.fr", "besTennisPlayer");
    when(userRepository.findAll()).thenReturn(List.of(user1, user2));

    mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].pseudo").value("leoMessi"));
  }

  @Test
  public void shouldGetUser() throws Exception {
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


  @Test
  public void shouldNotGetUser() throws Exception {
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
            .andExpect(status().isNotFound());
  }


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


}


/*
@SpringBootTest      // This means that the class test needs to launch a Spring context
@Sql("/data.sql")    // This annotation gives the path of the sql script that contains the database used for tests
public class UserControllerTest {
  @Autowired        // Injects dependencies
  private UserController userController;

  @Test
  public void shouldGetAllUsers(){
    List<User> users = userController.getUsers();

    assertEquals(4, users.size());
    assertEquals("mikeTyson", users.get(0).getPseudo());
  }

  @Test
  public void shouldFindUser(){
  }


}*/
