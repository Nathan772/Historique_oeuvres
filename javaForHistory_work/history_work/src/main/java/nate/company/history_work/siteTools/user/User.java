package nate.company.history_work.siteTools.user;

import jakarta.persistence.*;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Entity
@Component
/* table est utilisé car
il existe déjà une table de nom "User" donc on renomme
avec @Table name="..." en précisant le nouveau nom de la table que l'on souhaite créer
 */
@Table(name="user")
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
    private String pseudo;
    private String email;
    private String password;
    private String category = "average";

    /**
     *
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

    /*
    Pour faire fonctionner l'API il faut au minimum :
    le constructeur standard, les getters, les setters, et toString
     */
    public User(String pseudo, String email, String password){
        Objects.requireNonNull(pseudo, "the user's pseudo cannot be null");
        Objects.requireNonNull(email, "the user's email cannot be null");
        Objects.requireNonNull(password, "the user's password cannot be null");
        this.pseudo = pseudo;
        this.email = email;
        this.password = password;
    }

    public User(long id,String pseudo, String email, String password, String category){
        this.id = id;
        this.email = email;
        this.password = password;
        this.category =category;
    }

    /**
     * getter on Pseudo
     * @return
     * the psuedo of the user
     */
    public String getPseudo(){
        return pseudo;
    }

    /**
     * a getter on the user's idUser.
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
    public String getPassword(){
        return password;
    }

    /**
     * getter on the email
     * @return
     * the email of the user
     */
    public String getEmail(){
        return email;
    }

    /**
     * getter on the category
     * @return
     * the category of the user (admin, average)
     */
    public String getCategory(){
        return category;
    }


    /**
     * a setter on the user's id
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     *
     * setter on the password
     *
     */
    public void setPassword(String newPassword){
        password = newPassword;
    }

    /**
     *
     * @param category
     * the category of the user ("admin" or "average)
     * setter on the category
     *
     */
    public void setCategory(String category){
        category = category;
    }

    /**
     *
     * setter on the email
     *
     */
    public void setEmail(String newEmail){
         email = newEmail;
    }

    /**
     *
     * setter on the name
     *
     */
    public void setPseudo(String newPseudo){
         pseudo = newPseudo;
    }
    @Override
    public String toString(){
        return "Utilisateur numéro : "+id+ ", pseudo : "+pseudo+", email "+email + " statut : "+category;
    }


}
