package nate.company.history_work.siteTools.reaction;

//import jakarta.persistence.*;
//import nate.company.history_work.siteTools.anime.AnimeShort;
//import nate.company.history_work.siteTools.movie.Movie;
//import nate.company.history_work.siteTools.user.User;
//import org.springframework.stereotype.Component;
//
//import java.util.Objects;
//
//@Entity
//@Component
//public class AnimeShortReaction extends Reaction {
//
//    @Id
//    @Column(name="id_reaction")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @ManyToOne
//    @JoinColumn(name="idAnime", nullable=false)
//    private AnimeShort animeShortReacted;
//
//    public AnimeShortReaction(long id, User reactioner, ReactionChoices reaction, AnimeShort animeShortReacted){
//        //set les champs extends
//        super(reactioner, reaction);
//        this.animeShortReacted = animeShortReacted;
//        //this.id = id;
//    }
//
//    public AnimeShortReaction(User reactioner, ReactionChoices reaction, AnimeShort animeShort){
//        //set les champs extends
//        super(reactioner, reaction);
//        this.animeShortReacted = animeShort;
//    }
//
//    public AnimeShortReaction(){
//        super();
//    }
//
////    public AnimeShort getAnimeShort() {
////        return getAnimeShort();
////    }
//
//    public void setAnimeShortReacted(AnimeShort animeShortReacted) {
//        Objects.requireNonNull(animeShortReacted);
//        this.animeShortReacted = animeShortReacted;
//    }
//
//
//    public AnimeShort getAnimeShortReacted() {
//        return animeShortReacted;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//}
