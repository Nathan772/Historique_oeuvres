package nate.company.youtube_converter.siteTools;

import jakarta.persistence.*;
import org.springframework.context.annotation.Bean;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;

    /**
     *
     * constructeur par défaut, c'est à dire, avec 0 arguments, indispensable pour résoudre l'erreur
     * "required a bean of type "java.lang.String" that could not be found"
     */

    public User(){
        this.name = "";
        this.email = "";
    }

    /*
    Pour faire fonctionner l'API il faut au minimum :
    le constructeur standard, les getters, les setters, et toString
     */
    public User(String name, String email){
        Objects.requireNonNull(name, "the user's name cannot be null");
        Objects.requireNonNull(email, "the user's email cannot be null");
        this.name = name;
        this.email = email;
    }

    /**
     * getter on name
     * @return
     * the name of the user
     */
    public String getName(){
        return name;
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
     * a setter on the user's id
     */
    public void setId(Long id){
         this.id = id;
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
    public void setName(String newName){
         name = newName;
    }
    @Override
    public String toString(){
        return "Utilisateur numéro : "+id+ ", nom : "+name+", email "+email;
    }


}
