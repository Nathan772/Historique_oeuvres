package nate.company.history_work.siteTools.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import nate.company.history_work.siteTools.dtos.reaction.MovieReactionDto;
import nate.company.history_work.siteTools.movie.Movie;

import java.util.*;
import java.util.stream.Collectors;

public class MovieDto {
    private long id;
    private String title;
    private int yearOfRelease;

    private String imdbID;
    //director of the movie
    private String director;

    /*
    - One to many means, one set will possess many users but a user will just be
    - many to many means a user will possess several movies and a movie will possess several users
    - watchmovies is the name of the field connected in the "user" object to the list
    of the "User" Object.*/

    /*
    back reference means there's another "managed reference"
    related to isWatchedBy (in my case it's in userDto, it's in watchMovies "JsonManagedReference")
    therefore, isWatchedBy won't handle the jsonificatio, it's watchMovies that possess @JsonManagedReference
    that will handle (you could have reverse who possess "backRef" and who possess "managedReference"
     */
    @JsonBackReference
    private Set<UserDto> isWatchedBy = new HashSet<UserDto>();


    private String poster;

    @JsonBackReference
    private Set<MovieReactionDto> reactions = new LinkedHashSet<>();

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
    @JsonCreator
    public MovieDto(long idmovie, String title, int yearOfRelease, String imdbID, String director,

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

    /*
    ignore the user by default to prevent from
    nested infinite copies
     */
    public MovieDto(Movie movie) {
        //Objects.requireNonNull(movie, "the movie cannot be null");
//        this.id = movie.getId();
//        this.yearOfRelease = movie.getYearOfRelease();
//        this.imdbID = movie.getImdbID();
//        this.director = movie.getDirector();
//        this.title = movie.getTitle();
//        this.poster = movie.getPoster();
//        //cause error for now...
//        //this.isWatchedBy = new HashSet<>();
//        this.isWatchedBy = movie.getIsWatchedBy().stream().map(user -> new UserDto(user, true)).collect(Collectors.toSet());
        this(movie, true);
    }
    public MovieDto(Movie movie, boolean ignore) {
        Objects.requireNonNull(movie, "the movie cannot be null");
        this.id = movie.getId();
        this.yearOfRelease = movie.getYearOfRelease();
        this.imdbID = movie.getImdbID();
        this.director = movie.getDirector();
        this.title = movie.getTitle();
        this.poster = movie.getPoster();
        //ignore copy of set
        if(!ignore) {
            System.out.println("on est censé ignorer le watchedBy");
            this.isWatchedBy = movie.getIsWatchedBy().stream().map(user -> new UserDto(user, true)).collect(Collectors.toSet());
        }
        else {
            System.out.println("on a pas ignoré le watchedBy");
        }
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

    public Set<UserDto> getIsWatchedBy() {
        return isWatchedBy;
    }

    public void setIsWatchedBy(Set<UserDto> userList){
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

    /**
     * Indicates whether some other object is "equal to" this Movie.
     *
     * @param o object to compare with the instance of movie.
     * @return true if this object is the same as the one given in argument, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        return o instanceof MovieDto movie
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
}
