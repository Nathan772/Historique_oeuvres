package nate.company.history_work.siteTools.movie;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovieRepositoryTest {

  @Autowired
  public MovieRepository movieRepository;
  @Test
  public void contextLoads(){
    var movies = movieRepository.findAll();
    assertEquals(1, 1);
  }
}