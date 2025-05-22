package nate.company.history_work.siteTools.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
//import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.person.Person;
//import nate.company.history_work.siteTools.reaction.AnimeShortReaction;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

//public class AnimeDto {
//    private long id;
//    private String title;
//    private int yearOfRelease;
//
//    private String imdbID;
//
//    private Set<AnimeShortReaction> reactions = new LinkedHashSet<>();
//
//    /*
//  all the anime written by a person.
//   */
//
//    private HashSet writers = new LinkedHashSet<PersonDto>();
//
//    /*
//  all the anime where this person is a voice actor/actor.
//   */
//    private HashSet actors = new LinkedHashSet<PersonDto>();
//
//    public AnimeDto(AnimeShort animeShort){
//
//    }
//    @JsonCreator
//    public AnimeDto(long id, String title, int yearOfRelease, String imdbID, HashSet<Person> writers, HashSet<Person> actors){
//        Objects.requireNonNull(title);
//        Objects.requireNonNull(imdbID);
//        Objects.requireNonNull(writers);
//        Objects.requireNonNull(actors);
//        if(id < 0){
//            throw new IllegalArgumentException("id cannot be lower than 0");
//        }
//        if(yearOfRelease < 0){
//            throw new IllegalArgumentException("year of releae for an anime cannot be lower than 0");
//        }
//
//    }
//}
