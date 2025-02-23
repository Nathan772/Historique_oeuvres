/**
 * This part is used by both code that manages users and the one that
 * manages movies.
 *
 * This file contains a structure that gathers a user id and a related movie he watched.
 */

package nate.company.history_work.siteTools.watch_read;

import jakarta.persistence.*;

import nate.company.history_work.controller.movie.MovieController;
import nate.company.history_work.controller.user.UserController;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.Date;

/**
 * Binds a user to a movie he watched or is planning to watch. It manages information like the last time of the movie
 * he watched and the viewing state of the movie.
 *
 * @author Nathan BILINGI
 * @author Dylan DE JESUS
 * @see MovieController
 * @see UserController
 */
@Entity
@Component
@Table(name="watchmovie")
public class WatchMovie {

    /**
     * The id of the watchmovie within the database.
     */
    @Id
    @Column(name="idwatchmovie")
    private long id;

    /**
     * The id of the user in the database.
     */
    private long iduser;

    /**
     * The id of the movie in the database.
     */
    private long idmovie;

    /**
     * <p>
     *     The state of the movie.
     * </p>
     * <ul>
     *      <li>"à regarder plus tard"</li>
     *      <li>"à revoir"</li>
     *      <li>"fini"</li>
     *      <li>"en cours de visionnage"</li>
     * </ul>
     */
    private String currentState = "à regarder plus tard";

    /**
     * The date of last registration of movie status. By default, it is initialized
     * at the current time when allocated.
     */
    private Date lastUpdate = new Date();

    /**
     * The last moment watched by the user. It is initialized at 00:00:00.
     */
    private Time lastMoment = new Time(0);

    /**
     * <p>Constructs a WatchMovie.
     * </p>
     * <br>
     * <p>This default constructor is necessary for bean... but cannot be actually used because of foreign key issue.
     * </p>
     */
    public WatchMovie(){}

    /**
     * Constructs a WatchMovie.
     *
     * @param idUser the id of the user watching the movie
     * @param idmovie the id of the movie
     * @param currentState the description of the viewing state of the movie
     */
    public WatchMovie(long idUser, long idmovie, String currentState){
        if(idUser < 0){
            throw new IllegalArgumentException("user's watching a movie ID cannot be lower than 0");
        }
        if(idmovie < 0){
            throw new IllegalArgumentException("movie's ID cannot be lower than 0");
        }

        this.iduser = idUser;
        this.idmovie = idmovie;
        this.currentState = currentState;
    }

    /**
     * Constructs a WatchMovie.
     *
     * @param idUser the id of the user watching the movie
     * @param idmovie the id of the movie
     * @param currentState the description of the viewing state of the movie
     * @param lastMoment the time when the user paused the movie
     */
    public WatchMovie(long idUser, long idmovie, String currentState, Time lastMoment){
        if(idUser < 0){
            throw new IllegalArgumentException("user's watching a movie ID cannot be lower than 0");
        }
        if(idmovie < 0){
            throw new IllegalArgumentException("movie's ID cannot be lower than 0");
        }
        this.iduser = idUser;
        this.idmovie = idmovie;

        this.currentState = currentState;
        this.lastMoment = lastMoment;
    }

    /**
     * Retrieves the id of the watchMovie.
     *
     * @return the id of the tab
     */
    public long getIdWatchMovie(){
        return id;
    }

    /**
     * Retrieves the user's id.
     *
     * @return the id of the user that watch the movie
     */
    public long getIdUser(){
        return iduser;
    }

    /**
     * Retrieves the id of the movie watched.
     *
     * @return the id of the movie watched
     */
    public long getIdMovie(){
        return idmovie;
    }

    /**
     * <p>Retrieves the state of watching of the movie
     * </p>
     * <ul>
     *     <li>"à regarder plus tard"</li>
     *     <li>"à revoir"</li>
     *     <li>"fini"</li>
     *     <li>"en cours de visionnage"</li>
     * </ul>
     *
     * @return the viewing state of the movie, as a String sentence
     */
    public String getCurrentState(){
        return currentState;
    }

    /**
     * getter on the date of the last update of the movie.
     * @return
     * the date of the last update of the movie status.
     */
    public Date getLastUpdate(){
        return lastUpdate;
    }

    /**
     * getter on the time of the last moment seen in the movie.
     * @return
     * the time of the last moment seen in the movie.
     */
    public Time getLastMoment(){
        return lastMoment;
    }

    /**
     * Sets a new value the lastUpdate done regarding the movie status.
     *
     * @param lastUpdate the last update of the movie.
     */
    public void setLastUpdate(Date lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**
     * Sets the last moment watched of the movie.
     *
     * @param lastMoment the time that describes the last moment watched.
     */
    public void setLastMoment(Time lastMoment){
        lastMoment = lastMoment;
    }

    /**
     * <p>Sets a new viewing state of the movie. It can take values such as the following :
     * </p>
     * <ul>
     *     <li>"à voir plus tard"</li>
     *     <li>"à revoir"</li>
     *     <li>...</li>
     * </ul>
     *
     * @param currentState the state of the watched movie.
     */
    public void setCurrentState(String currentState){
        this.currentState = currentState;
    }

    /**
     * Sets a new id of the movie.
     *
     * @param idmovie the id of the movie watched
     */
    public void setIdMovie(long idmovie){
        this.idmovie = idmovie;
    }

    /**
     * Sets the new user's id.
     *
     * @param iduser the new user's id.
     */
    public void setIdUser(long iduser){
        this.iduser = iduser;
    }

    /**
     * Sets a new id.
     *
     * @param idwatchMovie the new id.
     */
    public void setIdwatchMovie(long idwatchMovie){
        this.id = idwatchMovie;
    }

    /**
     * Returns a String representation of this {@link WatchMovie}. It provides information like the
     * {@link WatchMovie} id, the user's id, the movie id and the viewing state.
     *
     * @return a string representation of this {@link WatchMovie}.
     */
    @Override
    public String toString(){
        return "L'Id du tuple numéro : "+id+ ", l'id de l'utilisateur : "+iduser+" et celui du film : "+idmovie+", l'état de visionnage "+currentState;
    }
}