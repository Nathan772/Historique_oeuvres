package nate.company.history_work.siteTools.person;

import jakarta.persistence.*;
//import nate.company.history_work.siteTools.anime.AnimeShort;
import lombok.Getter;
import lombok.Setter;
import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.movie.Movie;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Component
@Table(name="person_table")
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_person")
    @Getter
    @Setter
    private long id;
    private String firstName;
    private String lastName;

    /*
    all the movies directed by a person
    as movie director.
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy="director", cascade = CascadeType.MERGE)
    private Set<Movie> moviesDirected = new LinkedHashSet<>();

    /*
  all the anime where this person is a voice actor/actor.
   */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy="actors", targetEntity = AnimeShort.class)
    private Set<AnimeShort> actorsAnime = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "anime_written_by_author",joinColumns = @JoinColumn(name = "idAnime"),inverseJoinColumns = @JoinColumn(name = "id_person"))
    private Set<AnimeShort> writtenAnime = new LinkedHashSet<>();

    public Person(long id, String firstName, String lastName, Set<Movie> moviesDirected, Set<AnimeShort> actorsAnime){
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        this.id = id;
        this.firstName= firstName;
        this.lastName =lastName;
        //this.moviesDirected = moviesDirected;
        this.actorsAnime = actorsAnime;
    }
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
        this.id = 0L;
        this.firstName= firstName;
        this.lastName =lastName;
    }

    public Person() {
        this.id = 0L;
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
                person.lastName.equals(person.lastName);
        //&&moviesDirected.equals(person.moviesDirected);
    }

    @Override
    public String toString() {
        return "id : "+id+" Director : lastName = "+lastName+" firstName = "+firstName+ " anime voice actors "+actorsAnime;
    }

    public void addMovieDirected(Movie movie){
        Objects.requireNonNull(movie);
        moviesDirected.add(movie);
    }

    public void addAnimeVoiced(AnimeShort animeShort){
        Objects.requireNonNull(animeShort);
        actorsAnime.add(animeShort);
    }


    public void setWrittenAnime(Set<AnimeShort> writtenAnime) {
        Objects.requireNonNull(writtenAnime);
        this.writtenAnime = writtenAnime;
    }

    public Set<AnimeShort> getWrittenAnime() {
        return writtenAnime;
    }

    public Set<AnimeShort> getActorsAnime() {
        return actorsAnime;
    }

//    public void setMoviesDirected(Set<Movie> moviesDirected) {
//        Objects.requireNonNull(moviesDirected);
//        this.moviesDirected = moviesDirected;
//    }

    public void setActorsAnime(Set<AnimeShort> actorsAnime) {
        Objects.requireNonNull(actorsAnime);
        this.actorsAnime = actorsAnime;
    }

//    public Set<Movie> getMoviesDirected() {
//        return moviesDirected;
//    }

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

    public Long getId() {
        System.out.println("l'id de la personne est :"+id);
        return id;
    }
}
