package nate.company.history_work.siteTools.wrapper;

import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
/*

a wrapper class necessary to enable
request with multiple parameters
in MovieController
 */
public class WrapperUserMovie {
    private User user;
    private Movie movie;

    public WrapperUserMovie(User user, Movie movie){
        this.movie =movie;
        this.user=user;
    }

    public Movie getMovie(){
        return movie;
    }

    public User getUser(){
        return user;
    }
}
