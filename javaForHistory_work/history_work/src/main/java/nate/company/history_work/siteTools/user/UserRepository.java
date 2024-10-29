package nate.company.history_work.siteTools.user;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CRUD which means Create, READ, UPDATE, DELETE
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long> {


}
