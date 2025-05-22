package nate.company.history_work.siteTools.watchedAnime;

import jakarta.transaction.Transactional;
import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchedAnimeRepository extends CrudRepository<WatchedAnime, Long> {
    @Query("SELECT wa FROM WatchedAnime wa WHERE wa.watcher = :user and wa.animeShort = :animeWatched")
    public List<WatchedAnime> findByUserAndAnime(User user, AnimeShort animeWatched);

    @Query("DELETE FROM WatchedAnime wa WHERE wa.id = :idWatchedAnime")
    @Modifying
    @Transactional
    public void removeById(long idWatchedAnime);
}
