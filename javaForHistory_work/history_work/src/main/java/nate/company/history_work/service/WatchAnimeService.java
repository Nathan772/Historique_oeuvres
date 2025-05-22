package nate.company.history_work.service;

//import nate.company.history_work.siteTools.anime.AnimeShort;
//import nate.company.history_work.siteTools.user.User;
//import nate.company.history_work.siteTools.watchedAnime.WatchedAnime;
//import nate.company.history_work.siteTools.watchedAnime.WatchedAnimeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class WatchAnimeService {
//    private final WatchedAnimeRepository watchedAnimeRepository;
//
//    @Autowired
//    public WatchAnimeService(WatchedAnimeRepository watchedAnimeRepository){
//        this.watchedAnimeRepository = watchedAnimeRepository;
//
//    }
//
//    public void saveWatchAnime(WatchedAnime watchedAnime){
//        /*
//        save the watched movie data
//         */
//        watchedAnimeRepository.save(watchedAnime);
//    }
//
//    public Optional<WatchedAnime> findByUserAndAnime(User user, AnimeShort animeShort){
//        /*
//        find the watched movie data
//         */
//        //movie
//        return watchedAnimeRepository.findByUserAndAnime(user, animeShort).stream().findFirst();
//    }
//
//    public void removeByIdAnime(long idWatchMovie){
//        /*
//        remove the watched movie data from data base
//         */
//        watchedAnimeRepository.removeById(idWatchMovie);
//    }
//}
