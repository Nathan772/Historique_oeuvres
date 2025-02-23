package nate.company.history_work.siteTools.wrapper;


import nate.company.history_work.controller.movie.MovieController;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;

import java.util.Objects;


/**
 * A wrapper class necessary to enable request with multiple parameters in {@link MovieController}.
 *
 * @author Nathan BILINGI
 * @author Dylan DE JESUS
 * @see MovieController
 */
public class WrapperUserMovie {
    private User user;
    private Movie movie;

    /**
     * Constructs a wrapper that contains both a movie and an associated user.
     *
     * @param user the initial user
     * @param movie the initial movie
     * @throws NullPointerException if user or movie is null
     */
    public WrapperUserMovie(User user, Movie movie) throws NullPointerException {
        this.movie = Objects.requireNonNull(movie);
        this.user = Objects.requireNonNull(user);
    }

    /**
     * Sets a new movie.
     *
     * @param movie the new movie.
     */
    public void setMovie(Movie movie){
        this.movie = movie;
    }

    /**
     * Sets a new user.
     *
     * @param user the new user.
     */
    public void setUser(User user){
        this.user = user;
    }

    /**
     * Retrieves the current movie.
     *
     * @return a movie.
     */
    public Movie getMovie(){
        return movie;
    }

    /**
     * Retrieves the current user.
     *
     * @return a user.
     */
    public User getUser(){
        return user;
    }
}
