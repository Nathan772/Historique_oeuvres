package nate.company.history_work.siteTools.watchedAnime;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.status.VisualArtStatus;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import nate.company.history_work.siteTools.watchedObject.WatchedObject;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Entity
@Component
@Table(name="watched_anime_table")
public class WatchedAnime extends WatchedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idWatchedAnime")
    private long id;

    @JoinColumn(name="idAnime")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private AnimeShort animeShort;


    /*
   necessary for spring init
   (ID is NECESSARY)
    */
    @JsonCreator
    public WatchedAnime(long id, User user, AnimeShort animeShort, VisualArtStatus status, long timeAsLong){
        super(timeAsLong,user, status);
        Objects.requireNonNull(animeShort);
        this.animeShort = animeShort;
        this.id = id;
    }

    public WatchedAnime(User user, AnimeShort animeShort, VisualArtStatus status, long timeAsLong){
//        Objects.requireNonNull(user);
//        Objects.requireNonNull(status);
//        Objects.requireNonNull(movie);
//        if(timeAsLong < 0){
//            throw new IllegalArgumentException("WATCHed movie time cannot be lower than 0");
//        }

        //this.watcher = user;
        super(timeAsLong, user, status);
        this.animeShort = animeShort;
        this.id = 0;
//        this.movieStatus = status;
//        this.timeAsLong = timeAsLong;
    }

    /*
    necessary for spring
     */
    public WatchedAnime(){
        super();
        this.id = 0;
        this.animeShort = new AnimeShort();
    }


    public AnimeShort getAnimeShort() {
        return animeShort;
    }

    public void setAnimeShort(AnimeShort animeShort) {
        this.animeShort = animeShort;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return animeShort.hashCode()^getWatcher().hashCode()^Long.hashCode(getId())^Long.hashCode(
                getTimeAsLong()
        )^getArtStatus().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WatchedAnime animeWatched && animeWatched.equals(animeWatched.animeShort) && animeWatched.getWatcher()
                .equals(getWatcher());
    }

}
