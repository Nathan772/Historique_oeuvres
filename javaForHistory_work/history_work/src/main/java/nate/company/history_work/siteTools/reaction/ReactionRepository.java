package nate.company.history_work.siteTools.reaction;

import nate.company.history_work.siteTools.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CRUD which means Create, READ, UPDATE, DELETE
 */
@Repository
public interface ReactionRepository extends CrudRepository<Reaction,Long> {
}
