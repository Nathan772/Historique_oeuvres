package nate.company.history_work.siteTools.wrapper;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
/*
@RestController

/*
permet de résoudre le problème de
"No Access-control-allow-origin"
cors policy error

@CrossOrigin("*")
public class WrapperUserMovieController {
    /*
   necessary constructor for REST API

    //standard constructors

    private final WrapperUserMovieRepository wrapperUserMovieRepository;

    /*
    constructeur créé par moi-même qui se remplit avec un paramètre de type UserRepo...
    C'est Spring qui gérera lui-même l'ajout/la création de l'argument lors de l'appel

    public WrapperUserMovieController(WrapperUserMovieRepository wrapperUserMovieRepository){
        Objects.requireNonNull(wrapperUserMovieRepository);
        this.wrapperUserMovieRepository = wrapperUserMovieRepository;
    }
}*/
