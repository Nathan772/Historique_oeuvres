package nate.company.history_work.siteTools.genericVisualArt;

import jakarta.persistence.*;
import nate.company.history_work.siteTools.reaction.Reaction;
import nate.company.history_work.siteTools.user.User;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

//@Entity
//@Table(name="generic_visual_art_table")
/*
mapped superclass is use
for inheritance
 */
@MappedSuperclass
public class GenericVisualArt {
    private String title;
    private int yearOfRelease;
    //many genre
    // for one movie
    //private String genre;

    private String imdbID;
    //director of the movie
    //private String director;

   /* @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_watch_movie",
            joinColumns = @JoinColumn(name = "movieid"),
            inverseJoinColumns = @JoinColumn(name = "userid"))
    private Set<User> watchers = new HashSet<User>();*/

    private String poster;

    /*
    default constructor for spring
     */
    public GenericVisualArt(){
        this.title = "bla";
        this.yearOfRelease = 0;
        this.imdbID ="sss";
        this.poster = "nothing";
    }
//    public GenericVisualArt(String title, int yearOfRelease, String imdbID, String poster){
//        Objects.requireNonNull(title, "the visualArt title cannot be null");
//        Objects.requireNonNull(imdbID, "the imdbID cannot be null");
//        Objects.requireNonNull(poster, "the poster cannot be null");
//        if(yearOfRelease < 0){
//            throw new IllegalArgumentException("visual art's year cannot be lower than 0");
//        }
//        this.title = title;
//        this.yearOfRelease = yearOfRelease;
//        this.imdbID = imdbID;
//        this.poster = poster;
//    }

    public GenericVisualArt(String title, int yearOfRelease, String imdbID, String poster){
        Objects.requireNonNull(title, "the visualArt title cannot be null");
        Objects.requireNonNull(imdbID, "the imdbID cannot be null");
        Objects.requireNonNull(poster, "the poster cannot be null");
        if(yearOfRelease < 0){
            throw new IllegalArgumentException("visual art's year cannot be lower than 0");
        }
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.imdbID = imdbID;
        this.poster = poster;
    }

    /**
     * Retrieves the title of the piece of art.
     * @return the title
     */
    public String getTitle(){
        return title;
    }


    /**
     * Retrieves the release year of the piece of art.
     * @return the year
     */
    public int getYearOfRelease(){
        return yearOfRelease;
    }

    /**
     * retrieve the poster of a piece of art.
     * @return
     * the piece art's poster.
     */
    public String getPoster(){
        return poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    /**
     * update the poster data.
     * @param poster
     * the new poster image for the piece of art.
     */
    public void setPoster(String poster) {
        Objects.requireNonNull(poster);
        this.poster = poster;
    }

    /**
     * Sets a new year of release.
     *
     * @param yearOfRelease the year of the release of the piece of art.
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

}
