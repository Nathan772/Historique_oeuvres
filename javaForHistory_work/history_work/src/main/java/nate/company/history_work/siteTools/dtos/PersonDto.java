package nate.company.history_work.siteTools.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import nate.company.history_work.siteTools.person.Person;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class PersonDto {

    private long id;
    private String firstName;
    private String lastName;

    /*
    all the movies directed by a person
    as movie director.
     */
    private HashSet moviesDirected = new LinkedHashSet<>();

    /*
   all the anime written by a person.
    */
    private HashSet writtenAnime = new LinkedHashSet<>();

    /*
  all the anime where this person is a voice actor/actor.
   */
    private HashSet actorsAnime = new LinkedHashSet<>();

    public PersonDto(Person person){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.id = person.getId();
    }
    @JsonCreator
    public PersonDto(long id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
