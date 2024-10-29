package nate.company.history_work.controller.watch_read;
import nate.company.history_work.siteTools.user.UserRepository;
import nate.company.history_work.siteTools.watch_read.WatchMovie;
import nate.company.history_work.siteTools.watch_read.WatchMovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController

/*
permet de résoudre le problème de
"No Access-control-allow-origin"
cors policy error
 */
@CrossOrigin("*")
public class WatchMovieController {
    /*
   necessary constructor for REST API
     */
    //standard constructors

    private final WatchMovieRepository watchMovieRepository;

    public WatchMovieController(WatchMovieRepository watchMovieRepo){
        Objects.requireNonNull(watchMovieRepo);
        this.watchMovieRepository = watchMovieRepo;
    }
}
