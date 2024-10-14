package nate.company.history_work.siteTools;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CRUD, c'est à dire : Create, READ, UPDATE, DELETE
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long> {


}
