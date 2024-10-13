package nate.company.youtube_converter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
-ajoutez l'annotation @RestController dans votre controller, pour dire que ce sera une représentation pour les API REST.
Representational state transfer.
 */

@RestController
/*

indique les URL que l'on veut traiter dans ce controller
path="test" signifie que tout ce qui appelle "test" dans l'url attérit ici
 */

@RequestMapping(path="test")
public class TestController {
    /*
    Le getMapping signifie que toutes les méthodes qui seront associées qui appellent "test" (à cause du premier path) et "string"
     (deuxième path) dans l'url, attériront ici
     */
    /*
    renvoie une chaine de caractère
     */
    @GetMapping(path="string")
    public String getString(){
        return "Chaine de caractère transmise par YoutubeConverter";
    }
}

