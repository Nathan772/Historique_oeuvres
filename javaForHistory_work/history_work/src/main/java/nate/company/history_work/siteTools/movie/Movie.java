package nate.company.history_work.siteTools.movie;

import jakarta.persistence.*;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * This class is the representation of a movie in the database.
 *
 * @author Nathan BILINGI
 * @author Dylan DE JESUS
 */
@Entity
@Component
/*
name = movie, enable to be recognized as "movie" in database
 */
@Table(name="movie")
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
    private long id;
    private String title;
    private int yearOfRelease;
    //many genre
    // for one movie
    //private String genre;

    private String imdbID;
    //director of the movie
    private String director;

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
    public Movie(long idmovie, String title, int yearOfRelease, String imdbID, String director){
        Objects.requireNonNull(title, "the movie's title cannot be null");
        Objects.requireNonNull(imdbID, "the imdbID cannot be null");
        Objects.requireNonNull(director, "the movie director cannot be null");
        if(yearOfRelease < 0){
            throw new IllegalArgumentException("movie's year cannot be null");
        }
        if(idmovie < 0){
            throw new IllegalArgumentException("movie's id cannot be null");
        }
        this.id = idmovie;
        this.yearOfRelease = yearOfRelease;
        this.imdbID = imdbID;
        this.director = director;
        this.title = title;
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
     * Retrieves the category.
     *
     * @return the category of the user (admin, average)
     */
    public String getImdbID(){
        return imdbID;
    }

    /**
     * Sets a new user's id.
     *
     * @param id the new id
     */
    public void setMovieId(Long id){
        this.id = id;
    }

    /**
     * Sets a new password.
     *
     * @param newDirector the new director name
     */
    public void setDirector(String newDirector){
        this.director = newDirector;
    }

    /**
     * Sets a new year of release.
     *
     * @param yearOfRelease the year of the movie release.
     */
    public void setYearOfRelease(int yearOfRelease){
        this.yearOfRelease = yearOfRelease;
    }

    /**
     * Sets a new email address.
     *
     * @param newTitle the new title
     */
    public void setTitle(String newTitle){
        title = newTitle;
    }

    /**
     * Sets a new Imdb id.
     *
     * @param newImdbId  the new id
     */
    public void setImdbID(String newImdbId){
        imdbID = newImdbId;
    }

    @Override
    public String toString(){
        return "L'id du film : "+id+ ", titre : "+title+", directeur "+director + " année : "+ yearOfRelease;
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
}

