package nate.company.history_work.service;

//import nate.company.history_work.siteTools.anime.AnimeShort;
//import nate.company.history_work.siteTools.anime.AnimeShortRepository;
//import nate.company.history_work.siteTools.movie.Movie;
//import nate.company.history_work.siteTools.user.User;
//import nate.company.history_work.siteTools.watchedAnime.WatchedAnimeRepository;
//import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
//import nate.company.history_work.siteTools.watchedMovie.WatchedMovieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Objects;
//import java.util.Optional;
//
//@Service
//public class AnimeShortService {
//    private final AnimeShortRepository animeShortRepository;
//
//    @Autowired
//    public AnimeShortService(AnimeShortRepository animeShortRepository){
//        this.animeShortRepository = animeShortRepository;
//    }
//
//    public void saveAnimeShort(AnimeShort animeShort){
//        /*
//        save the watched anime data
//         */
//        animeShortRepository.save(animeShort);
//    }
//
//    public Optional<AnimeShort> getAnimeShortById(long idAnimeShort){
//        var animeShort = animeShortRepository.getById(idAnimeShort);
//        if(animeShort.isEmpty()){
//            return Optional.empty();
//        }
//        return animeShort;
//    }
//
//    public Optional<AnimeShort> getAnimeShortByImdb(String imdbId){
//        Objects.requireNonNull(imdbId);
//        var animeShort = animeShortRepository.getByimdbID(imdbId);
//        if(animeShort.isEmpty()){
//            return Optional.empty();
//        }
//        return animeShort;
//    }
//
//}
