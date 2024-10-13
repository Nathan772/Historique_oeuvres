package nate.company.youtube_converter.siteTools;

import jakarta.persistence.*;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Entity
@Component
/*
name="nomDeLaTablePourLaBDD"
 */
@Table(name="video")
public class Video {
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
    private String link;
    private String title;

    /**
     *
     * constructeur par défaut, c'est à dire, avec 0 arguments, indispensable pour résoudre l'erreur
     * "required a bean of type "java.lang.String" that could not be found"
     */

    public Video(){
        this.link = "";
        this.title= "";
    }

    /*
    Pour faire fonctionner l'API il faut au minimum :
    le constructeur standard, les getters, les setters, et toString
     */
    public Video(String link, String title){
        Objects.requireNonNull(link, "the link for the video name cannot be null");
        this.link = link;
        this.title = title;
    }

    /**
     * getter on name
     * @return
     * the content of the link for the video
     */
    public String getLink(){
        return link;
    }

    /**
     * a getter on the user's id.
     * @return
     * the id of the video
     */
    public long getId(){
        return id;
    }

    /**
     * a getter on the Video title
     * @return
     * the title of the video.
     */
    public String getTitle(){
        return title;
    }

    /**
     * a setter on the user's id
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * a setter on the video title
     */
    public void setTitle(String title){
        this.title = title;
    }


    /**
     *
     * setter on the email
     *
     */
    public void setLink(String newLink){
        link = newLink;
    }


    @Override
    public String toString(){
        return "Vidéo numéro : "+id+ ", lien : "+link+ "le titre : "+title;
    }


}
