package nate.company.history_work.siteTools.movie;

import nate.company.history_work.siteTools.watch_read.WatchMovieRepository;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.repository.CrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the {@link MovieRepository} class.
 *
 * These tests are primarily intended as a training exercise for writing unit tests
 * for repositories within a Spring context in a full-stack application.
 *
 * The tests focus on verifying the methods provided by the {@link CrudRepository} class,
 * which {@link MovieRepository} inherits, similarly to how {@link UserRepository}
 * and {@link WatchMovieRepository} work.
 *
 */
@SpringBootTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void shouldGetAllMovies(){
        var movies = new ArrayList<>();
        movieRepository.findAll().forEach(movies::add);
        var nbMovies = movieRepository.count();

        assertEquals(5, movies.size(), nbMovies);
    }

    @Test
    public void shouldGetMovieByID(){
        var expectedMovie = new Movie(1L, "Barbie", 2023, "ID_1", "Greta Gerwig");
        var secondExpectedMovie = new Movie(3L, "Gladiator", 2000, "ID_3",
                "Ridley Scott");
        var movie = movieRepository.findById(1L);
        var secondMovie = movieRepository.findById(3L);

        // First Movie
        assertTrue(movie.isPresent());
        assertEquals(expectedMovie.getId(), movie.get().getId());
        assertEquals(expectedMovie.getTitle(), movie.get().getTitle(), "Barbie");
        assertEquals(expectedMovie.getYear(), movie.get().getYear());

        // Second Movie
        assertTrue(movie.isPresent());
        assertEquals(secondExpectedMovie.getId(), secondMovie.get().getId());
        assertEquals(secondExpectedMovie.getTitle(), secondMovie.get().getTitle(), "Gladiator");
        assertEquals(secondExpectedMovie.getYear(), secondMovie.get().getYear());
    }

    @Test
    public void shouldNotGetMovieByID(){
        var firstWrongTrial = movieRepository.existsById(10L); // ID higher than the highest stored in the db
        var secondWrongTrial = movieRepository.existsById(-1L); // Negative ID
        var thirdWrongTrial = movieRepository.existsById(0L);   // The first ID value when inserting into the
                                                                         // db is 1
        assertFalse(firstWrongTrial);
        assertFalse(secondWrongTrial);
        assertFalse(thirdWrongTrial);
    }

    @Test
    public void shouldThrowErrorBecauseOfNullID(){
        InvalidDataAccessApiUsageException exception = assertThrows(InvalidDataAccessApiUsageException.class,
                () -> movieRepository.findById(null));

        assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
        assertEquals("The given id must not be null", exception.getMessage());
    }

    @Test
    public void shouldSaveMovie(){
        var movie = new Movie(6L, "Oppenheimer", 2023, "ID_6", "Christopher Nolan");;
        var savedMovie = movieRepository.save(movie);
        var nbMovies = movieRepository.count();
        var existsInDatabase = movieRepository.existsById(movie.getId());

        assertEquals(movie, savedMovie); // This means that the movie is new in database
                                         // otherwise savedMovie could be different than movie
        assertEquals(6, nbMovies);
        assertTrue(existsInDatabase);   // The id of the Movie is the same as the one in the database
                                        // (not always the case if the ID of the Movie isn't equal to the amount of
                                        // movies stored in the database)
    }

    @Test
    public void shouldDeleteMovieByID(){
        var nbMoviesBeforeDeletion = movieRepository.count();
        movieRepository.deleteById(2L);
        var nbMoviesAfterDeletion = movieRepository.count();

        assertEquals(nbMoviesBeforeDeletion, nbMoviesAfterDeletion + 1);
    }
}