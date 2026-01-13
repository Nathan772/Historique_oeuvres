package nate.company.history_work.siteTools.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.reaction.MovieReaction;
import nate.company.history_work.siteTools.reaction.Reaction;
//import nate.company.history_work.siteTools.watchedAnime.WatchedAnime;
import nate.company.history_work.siteTools.watchedAnime.WatchedAnime;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import org.springframework.stereotype.Component;
import src.main.java.nate.company.history_work.siteTools.user.UserCategory;

import java.util.*;

/**
 * This is the entity that represents a user.
 *
 * @author Nathan BILINGI
 * @author Dylan DE JESUS
 */
@Entity
@Component
// Table est utilisé car il existe déjà une table de nom User donc on
// renomme avec cette annotation
@Table(name="user_table")
@Data
@AllArgsConstructor
public class User {
    /*
    Id et generatedValue ont été
    importés manuellement en se référant aux noms présents
    en ligne sur le site : https://www.geeksforgeeks.org/how-to-add-external-jar-file-to-an-intellij-idea-project/
    Il faut choisir l'import associé à spring pour Id
    */

    /*
    Les noms des champs utilisés ici doivent matcher ceux présents en base de données,
    car ce sont ces valeurs, pour ces champs qui vont être entrées pour les tuples.
     */
    //attention l'annotation doit suivre directement les champs
    //on ne peut pas mettre de commentaire entre les 2

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
//    @Getter
//    @Setter
    private long id;

    @Column(name="pseudo", unique = true)
    private String pseudo;

    @Column(name="email", unique = true)
    private String email;
    @Column(name="password")
    private String password;

    @Column(name="category")
    private UserCategory category = UserCategory.AVERAGE;

    /*
    you must use genereic type for the type just like
    "List" not arrayList.
    This list is connected to Movie's "isWatchedBy" field
     */
    //@ManyToMany(fetch = FetchType.LAZY,mappedBy="movieIsWatchedBy", cascade = CascadeType.MERGE, targetEntity = Movie.class)
    //@JoinTable(name = "UserWatches", joinColumns =@JoinColumn(name="iduser") , inverseJoinColumns=@JoinColumn(name="idmovie"))
    //private Set<Movie> movies = new LinkedHashSet<>();

    @OneToMany(fetch= FetchType.LAZY, mappedBy = "watcher")
    private Set<WatchedMovie> watchMovies = new LinkedHashSet<>();



    //@ManyToMany(fetch = FetchType.LAZY,mappedBy="animeIsWatchedBy", cascade = CascadeType.PERSIST, targetEntity = AnimeShort.class)
    //@JoinTable(name = "UserWatches", joinColumns =@JoinColumn(name="iduser") , inverseJoinColumns=@JoinColumn(name="idmovie"))

    @OneToMany(fetch = FetchType.LAZY,mappedBy="watcher")
    private Set<WatchedAnime> watchAnime = new LinkedHashSet<>();


    @OneToMany(fetch = FetchType.LAZY,mappedBy="reactioner", cascade = CascadeType.ALL, targetEntity = MovieReaction.class)
    private Set<MovieReaction> reactions = new LinkedHashSet<>();

    /*
   necessary for spring init
   (ID is NECESSARY)
    */
    @JsonCreator
    public User(int id,
            String pseudo,
            String password,
            String email,
                UserCategory userCategory)

    {
        Objects.requireNonNull(email);
        Objects.requireNonNull(pseudo);
        this.pseudo = pseudo;
        this.password = password;
        this.email = email;
        this.id = id;
        this.category = userCategory;

    }

    /**
     * constructeur par défaut, c'est à dire, avec 0 arguments, indispensable pour résoudre l'erreur
     * "required a bean of type "java.lang.String" that could not be found"
     */
    public User(){
        this.pseudo = "";
        this.email = "";
        this.password = "";
        this.category =UserCategory.AVERAGE;
    }

    /**
     *  Pour faire fonctionner l'API il faut au minimum :
     *  le constructeur standard, les getters, les setters, et toString
     *
     * @param pseudo the pseudo of the user
     * @param email the email address of the user
     * @param password the password of the user
     */
    public User(String pseudo, String email, String password){
        Objects.requireNonNull(pseudo, "the user's pseudo cannot be null");
        Objects.requireNonNull(email, "the user's email cannot be null");
        Objects.requireNonNull(password, "the user's password cannot be null");
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructs a user.
     *
     * @param id the id of the user in the database
     * @param pseudo the pseudo of the user (credential)
     * @param email the email address (credential)
     * @param password the password (credential)
     * @param userCategory the category
     */
    public User(long id,String pseudo, String email, String password, UserCategory userCategory){
        this.id = id;
        this.email = email;
        this.pseudo = pseudo;
        this.password = password;
        this.category =userCategory;
    }

    /**
     * this method adds a movie watched
     * @param movieWatched
     * the movie watched
     */
    /*public void addWatchedMovie(Movie movieWatched, MovieStatus status, long time){
        Objects.requireNonNull(movieWatched);
        watchMovies.removeIf(new WatchedMovie(this, movieWatched, status, time));
    }*/

    /**
     * this method adds a movie watched
     * @param movieWatched
     * the movie watched
     */
    public void addWatchedMovie(WatchedMovie movieWatched){
        Objects.requireNonNull(movieWatched);
        //remove the old state
        //watchMovies.remove(movieWatched);

        //add the new state
        watchMovies.add(movieWatched);
    }

    /**
     * this method adds a movie watched
     //* @param movieWatched
     * the movie watched
     */
//    public void movie(WatchedMovie movieWatched){
//        Objects.requireNonNull(movieWatched);
//        //remove the old state
//        //watchMovies.remove(movieWatched);
//
//        //add the new state
//        watchMovies.add(movieWatched);
//    }

    //deprecatred

//    public void setWatchedMovies(Set<Movie> movies) {
//        this.movies = movies;
//    }
//
//    public Set<Movie> getWatchedMovies() {
//        return movies;
//    }

    /**
     * this method adds a anime watched
     // * @param animeWatched
     * the anime watched
     */

    public void addWatchedAnime(WatchedAnime animeWatched){
        Objects.requireNonNull(animeWatched);
        //remove the old state
        watchAnime.remove(animeWatched);

        //add the new state
        watchAnime.add(animeWatched);
    }



    public void setReactions(Set<MovieReaction> reactions) {
        Objects.requireNonNull(reactions);
        this.reactions = reactions;
    }

    public Set<MovieReaction> getReactions() {
        return reactions;
    }

    /**
     * remove a movie from the watch list
     * @param movieWatched
     */
    public void removeFromWatchedMovie(Movie movieWatched){
        Objects.requireNonNull(movieWatched);
        watchMovies.removeIf(movie->movie.getId() == movieWatched.getId());
    }

    /**
     * remove a movie from the watch list
     * @param animeShort
     */
    public void removeFromWatchedAnime(AnimeShort animeShort){
        Objects.requireNonNull(animeShort);
        watchAnime.removeIf(anime->anime.getId() == animeShort.getId());
    }
//deprecated
//    public Set<AnimeShort> getWatchAnime() {
//        return watchAnime;
//    }
//
//    public Set<Movie> getMovies() {
//        return movies;
//    }

//    public void setWatchAnime(Set<AnimeShort> watchAnime) {
//        this.watchAnime = watchAnime;
//    }

    /**
     * remove an anime from the watch list
     //* @param animeWatched
     */
//    public void removeFromWatchedAnime(AnimeShort animeWatched){
//        Objects.requireNonNull(animeWatched);
//        animeWatched.removeIf(animeWatched1->animeWatched1.getId() == animeWatched.getId());
//    }

    public void setWatchMovies(Set<WatchedMovie> watchMovies) {
        Objects.requireNonNull(watchMovies);
        this.watchMovies = watchMovies;
    }

    public Set<WatchedMovie> getWatchMovies() {
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
     * Retrieves the user's idUser.
     *
     * @return the id of the user
     */
    public long getId(){
        return id;
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

    /**
     * Retrieves the category.
     *
     * @return the category of the user (admin, average)
     */
    public UserCategory getCategory(){
        return category;
    }

    /**
     * Sets a new user's id.
     *
     * @param id the new id.
     */
    public void setId(long id){
        this.id = id;
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
    public void setCategory(UserCategory category){
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
        return "Utilisateur numéro : "+id+ ", pseudo : "+pseudo+", email "+email + " statut : "+category;
    }

    /**
     * add a reaction to a movie : like or dislike
     * @param movieReaction
     *
     */
    public void reactMovie(MovieReaction movieReaction){
        Objects.requireNonNull(movieReaction);
        //double like or double dislike
        //therefore : unreact
        var alreadyHere = unReactMovie(movieReaction);

        //add new reaction
        if(!alreadyHere) {
            reactions.add(movieReaction);
        }
    }

    /**
     * remove a reaction to a movie : like or dislike
     * @param movieReaction
     *
     * @return
     * true if it unreact, otherwise false.
     */
    public boolean unReactMovie(MovieReaction movieReaction){
        Objects.requireNonNull(movieReaction);
        return reactions.removeIf(movieReaction1-> movieReaction.getMovieReacted().equals(movieReaction1.getMovieReacted()));
    }

    @Override
    public int hashCode(){
        return Long.hashCode(id)^pseudo.hashCode()^ email.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User user1 && user1.id == id && user1.email.equals(email) && user1.pseudo.equals(pseudo)
                && user1.password.equals(password);
    }
}
