package nate.company.history_work.siteTools.movie;

import jakarta.persistence.*;

import org.springframework.stereotype.Component;

import java.util.Objects;

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

    /*
    Pour faire fonctionner l'API il faut au minimum :
    le constructeur standard, les getters, les setters, et toString
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
     * getter on Pseudo
     * @return
     * the psuedo of the user
     */
    public String getTitle(){
        return title;
    }

    /**
     * a getter on the user's id.
     * @return
     * the id of the user
     */
    public long getId(){
        return id;
    }

    /**
     * a getter on the user's password.
     * @return
     * the password of the user
     */
    public int getYearOfRelease(){
        return yearOfRelease;
    }

    /**
     * getter on the email
     * @return
     * the email of the user
     */
    public String getDirector(){
        return director;
    }

    /**
     * getter on the category
     * @return
     * the category of the user (admin, average)
     */
    public String getImdbID(){
        return imdbID;
    }


    /**
     * a setter on the user's id
     */
    public void setMovieId(Long id){
        this.id = id;
    }

    /**
     *
     * setter on the password
     *
     */
    public void setDirector(String newDirector){
        this.director = newDirector;
    }

    /**
     *
     *
     * @param yearOfRelease
     * the year of the movie release.
     *
     */
    public void setYearOfRelease(int yearOfRelease){
        this.yearOfRelease = yearOfRelease;
    }

    /**
     *
     * setter on the email
     *
     */
    public void setTitle(String newTitle){
        title = newTitle;
    }

    /**
     *
     * setter on the name
     *
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

