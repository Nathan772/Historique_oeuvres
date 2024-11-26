package nate.company.history_work.controller.movie;

import nate.company.history_work.siteTools.movie.Movie;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieControllerTest {
  @Autowired
  private MovieController test;

  // TODO : create a test to ensure we can't go for User's id  equals to 0

  // TODO : create a test to ensure we can't go for a user's id that doesn't exist

  // TODO : Change the configuration to not test this on the production database

  @Test
  public void firstTest(){
    List<Movie> movies = test.getUserMovies(1);
    assertEquals(1, movies.size());
  }
}