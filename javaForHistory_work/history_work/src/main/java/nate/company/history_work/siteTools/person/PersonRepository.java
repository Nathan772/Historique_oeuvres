package nate.company.history_work.siteTools.person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {
    public Optional<Person> getById(long id);

    @Query("SELECT pers FROM Person pers WHERE pers.firstName = :firstName and pers.lastName = :lastName")
    public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
}
