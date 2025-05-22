package nate.company.history_work.siteTools.movie;

import jakarta.persistence.*;

import nate.company.history_work.siteTools.genericVisualArt.GenericVisualArt;
import nate.company.history_work.siteTools.person.Person;
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
public class Movie extends GenericVisualArt {
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
    private long id;

    /*
    - One to many means, one set will possess many users but a user will just be
    - many to many means a user will possess several movies and a movie will possess several users
    - watchmovies is the name of the field connected in the "user" object to the list
    of the "User" Object.

     */


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_watch_movie",joinColumns = @JoinColumn(name = "movieid"),inverseJoinColumns = @JoinColumn(name = "userid"))
    private Set<User> movieIsWatchedBy = new HashSet<User>();

    //@Column(name="movie_poster")
    //private String poster;


    /*
    targetEntity : enable to find subclass
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy="movieReacted", cascade = CascadeType.ALL)
    private Set<MovieReaction> reactions = new LinkedHashSet<>();

    @ManyToOne
    private Person director;

    /**
     *
     * constructeur par défaut, c'est à dire, avec 0 arguments, indispensable pour résoudre l'erreur
     * "required a bean of type "java.lang.String" that could not be found"
     */
    public Movie(){
//        this.title = "bla";
//        this.yearOfRelease = 0;
//        this.imdbID ="sss";
//        this.director = "jean";
//        this.poster = "nothing";
        super();
        this.director = new Person("","");
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
    public Movie(String title, int yearOfRelease, String imdbID, Person director, String poster){
//        this.yearOfRelease = yearOfRelease;
//        this.imdbID = imdbID;
//        this.director = director;
//        this.title = title;
//        this.poster = poster;
        super(title, yearOfRelease, imdbID, poster);
        Objects.requireNonNull(director, "the movie director cannot be null");
        this.director = director;
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
    public Movie(long idmovie, String title, int yearOfRelease, String imdbID, Person director, String poster){
        super(title, yearOfRelease, imdbID, poster);
        this.id = idmovie;
        this.director = director;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the director of the movie.
     *
     * @return the name of the director
     */
    public Person getDirector(){
        return director;
    }






    /**
     * Sets a new password.
     *
     * @param newDirector the new director name
     */
    public void setDirector(Person newDirector){
        Objects.requireNonNull(newDirector);
        this.director = newDirector;
    }



    @Override
    public String toString(){
        return "L'id du film : "+getId()+ ", titre : "+getTitle()+", directeur : "+director + ", année : "+ getYearOfRelease();
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
                && getId() == movie.getId()
                && getTitle().equals(movie.getTitle())
                && getYearOfRelease() == movie.getYearOfRelease()
                && getImdbID().equals(movie.getImdbID())
                && director.equals(movie.director);
    }

    @Override
    public int hashCode() {
        return Long.hashCode(getId())^getTitle().hashCode()^getYearOfRelease()^getImdbID().hashCode()^director.hashCode()^getPoster().hashCode();
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

    public void setMovieIsWatchedBy(Set<User> movieIsWatchedBy) {
        Objects.requireNonNull(movieIsWatchedBy);
        this.movieIsWatchedBy = movieIsWatchedBy;
    }

    public Set<User> getMovieIsWatchedBy() {
        return movieIsWatchedBy;
    }

    /*
      this method adds a watcher
       */
    public void addIsWatchedBy(User user){
        Objects.requireNonNull(user);
        movieIsWatchedBy.add(user);
    }

    public void removeUserFromWatcher(User user){
        Objects.requireNonNull(user);
        movieIsWatchedBy.remove(user);

    }
}

