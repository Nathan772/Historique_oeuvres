package nate.company.history_work.siteTools.watch_read;

import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Entity
@Component

@Table(name="watchmovie")
public class WatchMovie {

    @Id
    @Column(name="idwatchmovie")
    private long id;
    private long iduser;
    private long idmovie;
    /*
    the state of watching of the movie :
    "à regarder plus tard",
    "à revoir",
    "fini",
    "en cours de visionnage"

     */
    //@Value("${props.currentState:'à regarder plus tard'}")
    private String currentState = "à regarder plus tard";

    private Date lastUpdate = new Date(new java.util.Date().getTime()/1000);
    //@Value("${props.currentState:'à regarder plus tard'}")
    private Time lastMoment = new Time(0,0,0) ;

    /**
     *
     * default constructor necessary for bean...
     * but cannot be actually used because of foreign key issue
     */

    public WatchMovie(){

    }

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
     * getter on idwatchMovie
     * @return
     * the id of the tab
     */
    public long getIdWatchMovie(){
        return id;
    }

    /**
     * a getter on the user's id.
     * @return
     * the id of the user that watch the movie
     */
    public long getIdUser(){
        return iduser;
    }

    /**
     * a getter on the id of the movie watched.
     * @return
     * the id of the movie watched.
     */
    public long getIdMovie(){
        return idmovie;
    }
    /*
    the state of watching of the movie :
    "à regarder plus tard",
    "à revoir",
    "fini",
    "en cours de visionnage"
     @return
     the state
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

    /*
    the state of watching of the movie :
    "à regarder plus tard",
    "à revoir",
    "fini",
    "en cours de visionnage"

     */
    /**
     * @param lastUpdate
     * the last update of the movie.
     *
     * a setter on the lastUpdate done regarding the movie status.
     */
    public void setLastUpdate(Date lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**
     * @param lastMoment
     *
     * setter on the last moment watched of the movie.
     *
     */
    public void setLastMoment(Time lastMoment){
        lastMoment = lastMoment;
    }

    /**
     *
     * a setter on the current state of the movie
     * ("à voir plus tard",à revoir,..." etc...
     * @param currentState
     * the state of the watched movie.
     *
     */
    public void setCurrentState(String currentState){
        this.currentState = currentState;
    }

    /**
     *
     * setter on the id of the movie.
     *
     * @param idmovie
     * the id of the movie watched
     *
     *
     *
     */
    public void setIdMovie(long idmovie){
        this.idmovie = idmovie;
    }

    /**
     *
     * setter on the name
     *
     */
    public void setIdUser(long iduser){
        this.iduser = iduser;
    }

    public void setIdwatchMovie(long idwatchMovie){
        this.id = idwatchMovie;
    }
    @Override
    public String toString(){
        return "L'Id du tuple numéro : "+id+ ", l'id de l'utilisateur : "+iduser+" et celui du film : "+idmovie+", l'état de visionnage "+currentState;
    }
}
