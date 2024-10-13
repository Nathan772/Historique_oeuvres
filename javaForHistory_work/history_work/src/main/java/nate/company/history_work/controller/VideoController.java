package nate.company.youtube_converter.controller;
import nate.company.youtube_converter.siteTools.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static nate.company.youtube_converter.siteTools.VideoParsing.startingPointForDownload;

/* cette
partie a été écrite manuellement il faut tjrs se référer à la page
https://www.baeldung.com/spring-boot-angular-web
 */
@RestController
/*
C'est le front qui est en 4200, mais cela permet justement de connecter le back au front
 */
//@CrossOrigin(origins="https://localhost:4200", maxAge =3600)
/*
permet de résoudre le problème de
"No Access-control-allow-origin"
cors policy error
 */
@CrossOrigin("*")
public class VideoController {

    /*
    constructeur nécessaire pour l'API REST
    créée par moi-même en s'inspirant du lien
    baledung ci-dessus.
     */
    //standard constructors

    private final VideoRepository videoRepository;

    /*
    constructeur créé par moi-même qui se remplit avec un paramètre de type UserRepo...
    C'est Spring qui gérera lui-même l'ajout/la création de l'argument lors de l'appel
     */
    public VideoController(VideoRepository videoRepository){
        Objects.requireNonNull(videoRepository);
        this.videoRepository = videoRepository;
    }

    /**
     * this method retrieves all the users from the database
     * elle est liée à la méthode "findAll" présente dans
     * user-service.service.ts
     * d'où le fait qu'elle appelle findAll()
     * @return
     */
    //@RequestMapping("/users")
    @GetMapping("/videos")
    public List<Video> getVideos(){
        return (List<Video>) videoRepository.findAll();
    }

    /**
     * this method enable to add a new video in the data based, when this one
     * is passed through the request body
     * normally we would need to add controller implementation, in order to check if user data is validate or not
     * but to keep it simple we didnt did it here but you can find how in the baledung page
     * at "Spring boot validation" click link.
     * Elle est liée à la méthode "save" de vide-dlservice.service.ts
     * d'où le fait qu'elle est de la form @PostMapping("/videos")
     * le "Post" de "PostMapping" est associé au "post" de this.http.post<Video>
     * le "/videos" est associé au "localhost:8080/videos" qui correspond au premier argument du post
     * de https.post<Video>
     * @param video
     */
    @PostMapping("/videos")
    public Video addVideo(@RequestBody Video video){
        System.out.println(" on ajoute la vidéo : "+video);
        //necessary to retrieve the actual id of the video for the
        // request on database

        //needs two actions to retrieve the actual name of the video does it ???

        //var actualVideo = videoRepository.save(video);

        System.out.println("on récupère la vidéo avec l'id : "+video.getId());
        //remplacer l'id de videoName par le title qui sera récupéré
        //save works just like update if the entity already existe with this

        /* préparation du fichier avec l'API Python */

        var videosLink = new String[1];
        videosLink[0] = video.getLink();

        //download
        startingPointForDownload(videosLink);

        //retrieve file's downloaded name on desktop

        var videoActualTitle = VideoParsing.retrieveVideoFileName(video.getLink());

        //video not found
        if(videoActualTitle == null){
            System.out.println(" video searched for its title, not found ");
            //remplacer par null
            return null;
        }

        /* update video title (begin) */

        video.setTitle(videoActualTitle);

        //save est équivalent à update, il met à jour si un objet existe déjà avec cet id
        videoRepository.save(video);

        /* update video title (end) */


        return video;
    }




    /**
     * the addVideo add a new video in the data based, when this one
     * is passed through the request body
     * normally we would need to add controller implementation, in order to check if user data is validate or not
     * but to keep it simple we didnt did it here but you can find how in the baledung page
     * at "Spring boot validation" click link.
     * Elle est liée à la méthode "save" de vide-dlservice.service.ts
     * d'où le fait qu'elle appelle save
     * C'est fonctionnel du pdv de postman
     * @param video
     */
    //fonctionne via postman
    // le transfert de données entre
    //les deux ne marchent que via des types associés à des controllers...
    // il faut encore tester en créant un type string en interface, mais peu crédible...
    /*
    à réutiliser
    @PostMapping("/videos/download")
    public Video downloadVideo(@RequestBody Video video){
        System.out.println("on télécharge la vidéo avec l'id : "+video.getId());
        //remplacer l'id de videoName par le title qui sera récupéré
        var videoInDb = videoRepository.findById(video.getId());

        //par l'API Python
        var videosLink = new String[1];
        videosLink[0] = video.getLink();
        startingPointForDownload(videosLink);

        if(videoInDb.isPresent()){
            return videoInDb.get();
        }
        return null;

    }*/

    /*
     * the addVideo add a new video in the data based, when this one
     * is passed through the request body
     * normally we would need to add controller implementation, in order to check if user data is validate or not
     * but to keep it simple we didnt did it here but you can find how in the baledung page
     * at "Spring boot validation" click link.
     * Elle est liée à la méthode "save" de vide-dlservice.service.ts
     * d'où le fait qu'elle appelle save
     * C'est fonctionnel du pdv de postman
     *
     * change "/videos/download" to "/videos/title"
     * @param video
     */
 //   @PostMapping("/videos/broadacast/title")
 //   public Video broadcastVideo(@RequestBody Video video){
//        System.out.println("on récupère la vidéo avec l'id : "+video.getId());
//        //remplacer l'id de videoName par le title qui sera récupéré
//        //save works just like update if the entity already existe with this
//
//        /* préparation du fichier avec l'API Python */
//
//
//        var videosLink = new String[1];
//        videosLink[0] = video.getLink();
//
//        //download
//        startingPointForDownload(videosLink);
//
//        //retrieve file's downloaded name on desktop
//
//        var videoActualTitle = VideoParsing.retrieveVideoFileName(video.getLink());
//
//        //video not found
//        if(videoActualTitle == null){
//            System.out.println(" video searched for its title, not found ");
//            return video;
//        }
//
//        /* update video title (begin) */
//
//        video.setTitle(videoActualTitle);
//
//        //save est équivalent à update, il met à jour si un objet existe déjà avec cet id
//        videoRepository.save(video);
//
//        /* update video title (end) */
//
//
//        return video;

//    }











    /**
     * this method enables to delete a user based on their id.
     * it is linked to delete from user-service.service hence the call to delete
     * @param id
     * the id of the user you want to remove
     */

    /**
     * remove method for videos.
     * Even if the mapping starts by delete, it starts implicitly by
     * You can try a request with postman to check if it actually works.
     * @param id
     * @return
     */

    @DeleteMapping("videos/delete/{id}")
    public ResponseEntity<String> removeVideo(@PathVariable String id){
        var videoIdLong = Long.parseLong(id);
        //var userIdLong = user.getId();
        //System.out.println("on supprime la musique avec l'id : "+videoIdLong);
        videoRepository.deleteById(videoIdLong);
        /* renvoyer un élément
        est indispensable et est nécessaire, sinon
        la suppression ne fonctionne pas.
        le retour d'un objet d'un type différent de ResponseEntity n'est pas accepté
        et provoquera un échec de la suppression
         */
        return ResponseEntity.noContent().build();
    }

}
