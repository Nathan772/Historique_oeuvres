package nate.company.history_work.siteTools.person;

import jakarta.persistence.*;
//import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.movie.Movie;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;

@Component
@Table(name="person_table")
@Entity
public class Person {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_person")
    private long id;
    private String firstName;
    private String lastName;

    /*
    all the movies directed by a person
    as movie director.
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy="director", cascade = CascadeType.ALL)
    private HashSet<Movie> moviesDirected = new LinkedHashSet<>();

    /*
   all the anime written by a person.
    */
//    @ManyToMany(fetch = FetchType.LAZY,mappedBy="writers", cascade = CascadeType.ALL)
//    private HashSet<AnimeShort> writtenAnime = new LinkedHashSet<>();

    /*
  all the anime where this person is a voice actor/actor.
   */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="actors", targetEntity = AnimeShort.class)
    private HashSet<AnimeShort> actorsAnime = new LinkedHashSet<>();

    public Person(long id, String firstName, String lastName){
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        this.id = id;
        this.firstName= firstName;
        this.lastName =lastName;
    }

    public Person(String firstName, String lastName){
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        this.id = 0;
        this.firstName= firstName;
        this.lastName =lastName;
    }

    public Person() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id)^firstName.hashCode()^lastName.hashCode();
    }

//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Person person && person.id == id && person.firstName.equals(person.lastName) &&
//                person.lastName.equals(person.lastName)&& actorsAnime.equals(person.actorsAnime) && writtenAnime.equals(person.writtenAnime)
//                && moviesDirected.equals(person.moviesDirected);
//    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Person person && person.id == id && person.firstName.equals(person.lastName) &&
                person.lastName.equals(person.lastName) &&
                 moviesDirected.equals(person.moviesDirected);
    }

//    public void setWrittenAnime(HashSet<AnimeShort> writtenAnime) {
//        Objects.requireNonNull(writtenAnime);
//        this.writtenAnime = writtenAnime;
//    }
//
//    public HashSet<AnimeShort> getWrittenAnime() {
//        return writtenAnime;
//    }
//
    public HashSet<AnimeShort> getActorsAnime() {
        return actorsAnime;
    }

    public void setMoviesDirected(HashSet<Movie> moviesDirected) {
        Objects.requireNonNull(moviesDirected);
        this.moviesDirected = moviesDirected;
    }

    public void setActorsAnime(HashSet<AnimeShort> actorsAnime) {
        Objects.requireNonNull(actorsAnime);
        this.actorsAnime = actorsAnime;
    }

    public HashSet<Movie> getMoviesDirected() {
        return moviesDirected;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
