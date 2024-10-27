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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idmovie;
    private String title;
    private int year;
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
    }

    /*
    Pour faire fonctionner l'API il faut au minimum :
    le constructeur standard, les getters, les setters, et toString
     */
    public Movie(long idmovie, String title, int year, String imdbID, String director){
        Objects.requireNonNull(title, "the movie's title cannot be null");
        Objects.requireNonNull(imdbID, "the imdbID cannot be null");
        Objects.requireNonNull(director, "the movie director cannot be null");
        if(year < 0){
            throw new IllegalArgumentException("movie's year cannot be null");
        }
        if(idmovie < 0){
            throw new IllegalArgumentException("movie's id cannot be null");
        }
        this.idmovie = idmovie;
        this.year = year;
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
    public long getIdMovie(){
        return idmovie;
    }

    /**
     * a getter on the user's password.
     * @return
     * the password of the user
     */
    public int getYear(){
        return year;
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
    public String imdbID(){
        return imdbID;
    }


    /**
     * a setter on the user's id
     */
    public void setMovieId(Long id){
        this.idmovie = id;
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
     * @param year
     * the year of the movie release.
     *
     */
    public void setYear(int year){
        this.year = year;
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
        return "L'id du film : "+idmovie+ ", titre : "+title+", directeur "+director + " année : "+year;
    }


}

