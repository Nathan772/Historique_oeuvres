package nate.company.history_work.siteTools.anime;

import jakarta.persistence.*;
import nate.company.history_work.siteTools.genericVisualArt.GenericVisualArt;
import nate.company.history_work.siteTools.person.Person;
import nate.company.history_work.siteTools.reaction.AnimeShortReaction;
import nate.company.history_work.siteTools.user.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Component
@Table(name="anime_table")
@Entity
public class AnimeShort extends GenericVisualArt {

    @Id
    @Column(name="idAnime")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(fetch = FetchType.LAZY,mappedBy="animeShortReacted", cascade = CascadeType.ALL)
    private Set<AnimeShortReaction> reactions = new LinkedHashSet<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_watch_anime",joinColumns = @JoinColumn(name = "idAnime"),inverseJoinColumns = @JoinColumn(name = "userid"))
    private Set<User> animeIsWatchedBy = new HashSet<User>();

    /*
  all the anime written by a person.
   */
//    @ManyToMany(fetch = FetchType.LAZY,mappedBy="writtenAnime", cascade = CascadeType.ALL)
//    private HashSet<Person> writers = new LinkedHashSet<>();

    //tmp need to be replaced by many to many
    private HashSet<Person> writers = new LinkedHashSet<>();

    /*
  all the anime where this person is a voice actor/actor.
   */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "anime_participate_actors",joinColumns = @JoinColumn(name = "idAnime"),inverseJoinColumns = @JoinColumn(name = "id_person"))
    private HashSet<Person> actors = new LinkedHashSet<>();

    public AnimeShort(){
        super();
    }

    public AnimeShort(String title, int yearOfRelease, String imdbID, String poster){
        super(title, yearOfRelease, imdbID, poster);
    }

    public AnimeShort(long id, String title, int yearOfRelease, String imdbID, String poster, HashSet actors, HashSet writers){
        super(title, yearOfRelease, imdbID, poster);
        this.id = id;
        this.writers.addAll(this.writers);
        this.actors.addAll(actors);
    }

    public AnimeShort(String title, int yearOfRelease, String imdbID, String poster, HashSet actors, HashSet writers){
        super(title, yearOfRelease, imdbID, poster);
        this.writers.addAll(this.writers);
        this.actors.addAll(actors);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AnimeShort animeShort && animeShort.getId() == getId()
                && animeShort.actors.equals(getActors()) && animeShort.writers.equals(getWriters())
                && animeShort.getPoster().equals(getPoster()) && animeShort.getImdbID().equals(getImdbID())
                && animeShort.getTitle().equals(getTitle()) && animeShort.getYearOfRelease() == getYearOfRelease();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(getId())^getTitle().hashCode()^Integer.hashCode(getYearOfRelease())^getImdbID().hashCode()
                ^actors.hashCode()^getPoster().hashCode()^ writers.hashCode();
    }

    /**
     * add a reaction to a movie : like or dislike
     * @param animeShortReaction
     *
     */
    public void reactUser(AnimeShortReaction animeShortReaction){
        Objects.requireNonNull(animeShortReaction);
        //double like or double dislike
        //therefore : unreact
        var alreadyHere = unReactUser(animeShortReaction);

        //add new reaction
        if(!alreadyHere) {
            reactions.add(animeShortReaction);
        }
    }

    /**
     * remove a reaction to a movie : like or dislike
     * @param animeShortReaction
     *
     */
    public boolean unReactUser(AnimeShortReaction animeShortReaction){
        Objects.requireNonNull(animeShortReaction);
        return reactions.removeIf(animeReaction1-> animeShortReaction.getReactioner().equals(animeReaction1.getReactioner()));
    }

    /*
 this method adds a watcher
  */
    public void addIsWatchedBy(User user){
        Objects.requireNonNull(user);
        animeIsWatchedBy.add(user);
    }

    public void removeUserFromWatcher(User user){
        Objects.requireNonNull(user);
        animeIsWatchedBy.remove(user);

    }

    public void setAnimeIsWatchedBy(Set<User> animeIsWatchedBy) {
        Objects.requireNonNull(animeIsWatchedBy);
        this.animeIsWatchedBy = animeIsWatchedBy;
    }

    public Set<User> getAnimeIsWatchedBy() {
        return animeIsWatchedBy;
    }

    public HashSet getActors() {
        return actors;
    }

//    public void setWriters(HashSet writers) {
//        Objects.requireNonNull(writers);
//        this.writers = writers;
//    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setActors(HashSet actors) {
        Objects.requireNonNull(actors);
        this.actors = actors;
    }

    public HashSet getWriters() {
        return writers;
    }


}
