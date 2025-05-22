package nate.company.history_work.siteTools.watchedAnime;

import jakarta.transaction.Transactional;
//import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
//public interface WatchedAnimeRepository extends CrudRepository<WatchedAnime, Long> {
//    @Query("SELECT wm FROM WatchedAnime wa WHERE wa.watcher = :user and wm.anime = :animeWatched")
//    public List<WatchedAnime> findByUserAndAnime(User user, AnimeShort animeWatched);
//
//    @Query("DELETE FROM WatchedAnime wa WHERE wa.id = :idWatchAnime")
//    @Modifying
//    @Transactional
//    public void removeById(long idWatchedAnime);
//}
