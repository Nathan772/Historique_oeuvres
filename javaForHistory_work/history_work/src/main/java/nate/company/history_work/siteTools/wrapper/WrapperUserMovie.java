package nate.company.history_work.siteTools.wrapper;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import org.springframework.stereotype.Component;

/*

a wrapper class necessary to enable
request with multiple parameters
in MovieController
 */
public class WrapperUserMovie {
    //private long idWrapper;
    private User user;
    private Movie movie;

    public WrapperUserMovie(User user, Movie movie){
        //default useless
        //this.idWrapper = 0;
        this.movie =movie;
        this.user=user;
    }

//    public void setIdWrapper(long idWrapper){
//        this.idWrapper = idWrapper;
//    }
    public void setMovie(Movie movie){
        this.movie = movie;
    }

    public void setUser(User user){
        this.user = user;
    }

//    public long getIdWrapper(){
//        return idWrapper;
//    }

    public Movie getMovie(){
        return movie;
    }

    public User getUser(){
        return user;
    }
}
