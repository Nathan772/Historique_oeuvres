package nate.company.history_work.service;

import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.person.Person;
import nate.company.history_work.siteTools.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Component
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;

    }

    public void savePerson(Person person){
        Objects.requireNonNull(person);
        personRepository.save(person);
    }

    public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName){
        return personRepository.findByFirstNameAndLastName(firstName, lastName);
    }
}
