package nate.company.history_work.siteTools.user;

import jakarta.persistence.*;

import nate.company.history_work.siteTools.movie.Movie;
import org.springframework.stereotype.Component;

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
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="iduser")
    private long id;

    @Column(name="pseudo")
    private String pseudo;

    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;

    @Column(name="category")
    private String category = "average";

    /*
    you must use genereic type for the type just like
    "List" not arrayList.
    This list is connected to Movie's "isWatchedBy" field
     */
    @ManyToMany(mappedBy="isWatchedBy")
    //@JoinColumn(name = "idmovie")
    private Set<Movie> watchMovies = new HashSet<>();

    /**
     * constructeur par défaut, c'est à dire, avec 0 arguments, indispensable pour résoudre l'erreur
     * "required a bean of type "java.lang.String" that could not be found"
     */
    public User(){
        this.id = 3;
        this.pseudo = "";
        this.email = "";
        this.password = "";
        this.category ="average";
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
     * @param category the category
     */
    public User(long id,String pseudo, String email, String password, String category){
        this.id = id;
        this.email = email;
        this.password = password;
        this.category =category;
    }

    /**
     * this method adds a movie watched
     * @param movieWatched
     * the movie watched
     */
    public void addWatchedMovie(Movie movieWatched){
        Objects.requireNonNull(movieWatched);
        watchMovies.add(movieWatched);
    }

    /**
     * remove a movie from the watch list
     * @param movieWatched
     */
    public void removeFromWatchedMovie(Movie movieWatched){
        Objects.requireNonNull(movieWatched);
        watchMovies.remove(movieWatched);
    }

    public void setWatchMovies(Set<Movie> watchMovies) {
        Objects.requireNonNull(watchMovies);
        this.watchMovies = watchMovies;
    }

    public Set<Movie> getWatchMovies() {
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
    public String getCategory(){
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
    public void setCategory(String category){
        category = category;
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
        return "Utilisateur numéro : "+id+ ", pseudo : "+pseudo+", email "+email + " statut : "+category
                +" il suit ou regarde actuellement :"+watchMovies.size()+" films";
    }

}
