package nate.company.history_work.siteTools.reaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReactionRepository extends CrudRepository<MovieReaction,Long> {
}
