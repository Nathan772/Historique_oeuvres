package nate.company.history_work.siteTools.movie;

import jakarta.persistence.*;

import nate.company.history_work.siteTools.reaction.MovieReaction;
import nate.company.history_work.siteTools.reaction.Reaction;
import nate.company.history_work.siteTools.user.User;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * This class is the representation of a movie in the database.
 *
 * @author Nathan BILINGI
 * @author Dylan DE JESUS
 */


/*
name = movie, enable to be recognized as "movie" in database
 */
@Component
@Table(name="movie_table")
@Entity
public class Movie {
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
    @Column(name="idmovie")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private int yearOfRelease;
    //many genre
    // for one movie
    //private String genre;

    private String imdbID;
    //director of the movie
    private String director;

    /*
    - One to many means, one set will possess many users but a user will just be
    - many to many means a user will possess several movies and a movie will possess several users
    - watchmovies is the name of the field connected in the "user" object to the list
    of the "User" Object.

     */


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_watch_movie",
            joinColumns = @JoinColumn(name = "movieid"),
            inverseJoinColumns = @JoinColumn(name = "userid"))
    private Set<User> isWatchedBy = new HashSet<User>();

    @Column(name="movie_poster")
    private String poster;


    /*
    targetEntity : enable to find subclass
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy="movieReacted", cascade = CascadeType.ALL)
    private Set<MovieReaction> reactions = new LinkedHashSet<>();

    /**
     *
     * constructeur par défaut, c'est à dire, avec 0 arguments, indispensable pour résoudre l'erreur
     * "required a bean of type "java.lang.String" that could not be found"
     */
    public Movie(){
        this.title = "bla";
        this.yearOfRelease = 0;
        this.imdbID ="sss";
        this.director = "jean";
        this.poster = "nothing";
    }

    /**
     * Constructs a movie.
     *
     * To make the API work we need at least :
     *  The standard constructor, getters, setters, toString.

     * @param title the title of the movie
     * @param yearOfRelease the release year of the movie
     * @param imdbID the imdb id
     * @param director the director name of the movie
     */
    public Movie(String title, int yearOfRelease, String imdbID, String director, String poster){
        Objects.requireNonNull(title, "the movie's title cannot be null");
        Objects.requireNonNull(imdbID, "the imdbID cannot be null");
        Objects.requireNonNull(director, "the movie director cannot be null");
        if(yearOfRelease < 0){
            throw new IllegalArgumentException("movie's year cannot be lower than 0");
        }
        this.yearOfRelease = yearOfRelease;
        this.imdbID = imdbID;
        this.director = director;
        this.title = title;
        this.poster = poster;
    }

    /**
     * Constructs a movie.
     *
     * To make the API work we need at least :
     *  The standard constructor, getters, setters, toString.
     *
     * @param idmovie the id of the movie
     * @param title the title of the movie
     * @param yearOfRelease the release year of the movie
     * @param imdbID the imdb id
     * @param director the director name of the movie
     */
    public Movie(long idmovie, String title, int yearOfRelease, String imdbID, String director,

                 String poster){
        Objects.requireNonNull(title, "the movie's title cannot be null");
        Objects.requireNonNull(imdbID, "the imdbID cannot be null");
        Objects.requireNonNull(director, "the movie director cannot be null");
        if(yearOfRelease < 0){
            throw new IllegalArgumentException("movie's year cannot be lower than 0");
        }
        if(idmovie < 0){
            throw new IllegalArgumentException("movie's id cannot be null");
        }
        this.id = idmovie;
        this.yearOfRelease = yearOfRelease;
        this.imdbID = imdbID;
        this.director = director;
        this.title = title;
        this.poster = poster;
    }

    /**
     * Retrieves the title of the movie.
     * @return the title
     */
    public String getTitle(){
        return title;
    }

    /**
     * Retrieves the id of the movie.
     *
     * @return the id of the movie
     */
    public long getId(){
        return id;
    }

    /**
     * Retrieves the release year of the movie.
     * @return the year
     */
    public int getYearOfRelease(){
        return yearOfRelease;
    }

    /**
     * Retrieves the name of the director of the movie.
     *
     * @return the name of the director
     */
    public String getDirector(){
        return director;
    }

    /**
     * retrieve the poster of a movie.
     * @return
     * the movie's poster.
     */
    public String getPoster(){
        return poster;
    }


    /**
     * Sets a new user's id.
     *
     * @param id the new id
     */
    public void setMovieId(long id){
        if(id < 0){
            throw new IllegalArgumentException("movie id cannot be lower than 0");
        }
        this.id = id;
    }

    /**
     * update the poster data.
     * @param poster
     * the new poster image for the movie.
     */
    public void setPoster(String poster) {
        Objects.requireNonNull(poster);
        this.poster = poster;
    }

    /**
     * Sets a new password.
     *
     * @param newDirector the new director name
     */
    public void setDirector(String newDirector){
        Objects.requireNonNull(newDirector);
        this.director = newDirector;
    }

    /**
     * Sets a new year of release.
     *
     * @param yearOfRelease the year of the movie release.
     */
    public void setYearOfRelease(int yearOfRelease){
        if(yearOfRelease < 0){
            throw new IllegalArgumentException("Movie's year of release cannot be lower than 0");
        }
        this.yearOfRelease = yearOfRelease;
    }

    /**
     * Sets a new email address.
     *
     * @param newTitle the new title
     */
    public void setTitle(String newTitle){
        Objects.requireNonNull(newTitle);
        title = newTitle;
    }

    /**
     * Sets a new Imdb id.
     *
     * @param newImdbId  the new id
     */
    public void setImdbID(String newImdbId){
        Objects.requireNonNull(newImdbId);
        imdbID = newImdbId;
    }

    @Override
    public String toString(){
        return "L'id du film : "+id+ ", titre : "+title+", directeur : "+director + ", année : "+ yearOfRelease;
    }

    /*
    this method adds a watcher
     */
    public void addIsWatchedBy(User user){
        Objects.requireNonNull(user);
        isWatchedBy.add(user);
    }

    public void removeUserFromWatcher(User user){
        Objects.requireNonNull(user);
        isWatchedBy.remove(user);

    }

    public Set<User> getIsWatchedBy() {
        return isWatchedBy;
    }

    public void setIsWatchedBy(Set<User> userList){
        isWatchedBy = userList;
    }

    public void setId(long id) {
        if(id < 0){
            throw new IllegalArgumentException("movie's id cannot be lower than 0");
        }
        this.id = id;
    }

    /**
     * get the imdb id of the movie
     * @return
     */
    public String getImdbID() {
        return imdbID;
    }

    public void setReactions(Set<MovieReaction> reactions) {
        Objects.requireNonNull(reactions);
        this.reactions = reactions;
    }

    public Set<MovieReaction> getReactions() {
        return reactions;
    }

    /**
     * Indicates whether some other object is "equal to" this Movie.
     *
     * @param o object to compare with the instance of movie.
     * @return true if this object is the same as the one given in argument, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        return o instanceof Movie movie
                && id == movie.id
                && title.equals(movie.title)
                && yearOfRelease == movie.yearOfRelease
                && imdbID.equals(movie.imdbID)
                && director.equals(movie.director);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id)^title.hashCode()^yearOfRelease^imdbID.hashCode()^director.hashCode()^poster.hashCode();
    }

    /**
     * add a reaction to a movie : like or dislike
     * @param movieReaction
     *
     */
    public void reactUser(MovieReaction movieReaction){
        Objects.requireNonNull(movieReaction);
        //double like or double dislike
        //therefore : unreact
        var alreadyHere = unReactUser(movieReaction);

        //add new reaction
        if(!alreadyHere) {
            reactions.add(movieReaction);
        }
    }

    /**
     * remove a reaction to a movie : like or dislike
     * @param movieReaction
     *
     */
    public boolean unReactUser(MovieReaction movieReaction){
        Objects.requireNonNull(movieReaction);
        return reactions.removeIf(movieReaction1-> movieReaction.getReactioner().equals(movieReaction1.getReactioner()));
    }
}

