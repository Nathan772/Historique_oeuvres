package nate.company.history_work.siteTools.person;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {
    public Optional<Person> getById(long id);
}
