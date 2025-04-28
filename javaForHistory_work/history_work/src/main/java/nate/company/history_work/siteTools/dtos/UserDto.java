package nate.company.history_work.siteTools.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto {

    private long id;
    private String pseudo;

    private String email;
    private String password;
    private src.main.java.nate.company.history_work.siteTools.user.UserCategory category = src.main.java.nate.company.history_work.siteTools.user.UserCategory.AVERAGE;

    /*
    managed reference prevent from infinite loop
    while jsonification is applied.
    This part will handle the jsonification
     */
    @JsonManagedReference
    private List<WatchedMovieDto> watchMovies = new ArrayList<>();

    /*
   necessary for spring init
   (ID is NECESSARY)
    */
    @JsonCreator
    public UserDto(
            long id,
                String pseudo,
                String password,
                String email,
                src.main.java.nate.company.history_work.siteTools.user.UserCategory userCategory,
            List<WatchedMovieDto> movieWatchedDto)

    {
        Objects.requireNonNull(pseudo, "the user's pseudo cannot be null");
        Objects.requireNonNull(email, "the user's email cannot be null");
        Objects.requireNonNull(password, "the user's password cannot be null");
        this.id = id;
        this.pseudo = pseudo;
        this.password = password;
        this.email = email;
        this.category = userCategory;
        this.watchMovies = movieWatchedDto;

    }

    public UserDto(
            User user)

    {
        //Objects.requireNonNull(user, "the user's pseudo cannot be null");
//        this.id = user.getId();
//        this.pseudo = user.getPseudo();
//        this.password = user.getPassword();
//        this.email = user.getEmail();
//        this.category = user.getCategory();
//        this.watchMovies = user.getWatchMovies().stream().map(movie->new MovieDto(movie)).toList();
        //by default don't ignore fields
        this (user, false);

    }

    /*
    ignore prevent from infinite loop due to nested copy.
    It enables to ignore fields that you will have to copy twice : normally and by loop.
     */
    public UserDto(
            User user, boolean ignore)

    {
        Objects.requireNonNull(user, "the user's pseudo cannot be null");
        this.id = user.getId();
        this.pseudo = user.getPseudo();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.category = user.getCategory();
        System.out.println("on est censé ignorer le watch Movie du user");
        if(!ignore) {
            System.out.println("on a pas ignoré le watch Movie du user");
            this.watchMovies = user.getWatchMovies().stream().map(movie -> new WatchedMovieDto(movie)).toList();
        }

    }




    /**
     * this method adds a movie watched
     * @param movieWatched
     * the movie watched
     */
    public void addWatchedMovie(WatchedMovieDto movieWatched){
        Objects.requireNonNull(movieWatched);
        watchMovies.add(movieWatched);
    }

    /**
     * remove a movie from the watch list
     * @param movieWatched
     */
    public void removeFromWatchedMovie(MovieDto movieWatched){
        Objects.requireNonNull(movieWatched);
        watchMovies.remove(movieWatched);
    }

    public void setWatchMovies(List<WatchedMovieDto> watchMovies) {
        Objects.requireNonNull(watchMovies);
        this.watchMovies = watchMovies;
    }

    public List<WatchedMovieDto> getWatchMovies() {
        return watchMovies;
    }

    /**
     * Retrieves the pseudo.
     *
     * @return the pseudo of the user
     */
    public String getPseudo(){
        return pseudo;
    }

    /**
     * Retrieves the user's password.
     *
     * @return the password of the user
     */
    public String getPassword(){
        return password;
    }

    /**
     * Retrieves the user's email address.
     *
     * @return the email of the user
     */
    public String getEmail(){
        return email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    /**
     * Retrieves the category.
     *
     * @return the category of the user (admin, average)
     */
    public src.main.java.nate.company.history_work.siteTools.user.UserCategory getCategory(){
        return category;
    }


    /**
     * Sets a new password.
     *
     * @param newPassword the new password
     */
    public void setPassword(String newPassword){
        password = newPassword;
    }

    /**
     * Sets a new category.
     *
     * @param category the category of the user ("admin" or "average)
     */
    public void setCategory(src.main.java.nate.company.history_work.siteTools.user.UserCategory category){
        this.category = category;
    }

    /**
     * Sets a new email address.
     *
     * @param newEmail the new email address
     */
    public void setEmail(String newEmail){
        email = newEmail;
    }


    /**
     * Sets a new pseudo.
     *
     * @param  newPseudo the new pseudo
     */
    public void setPseudo(String newPseudo){
        pseudo = newPseudo;
    }

    @Override
    public String toString(){
        //return "Utilisateur numéro : "+id+ ", pseudo : "+pseudo+", email "+email + " statut : "+category+" il suit ou regarde actuellement :"+watchMovies.size()+" films";
        return "pseudo : "+pseudo+", email "+email + " statut : "+category;
    }
}
