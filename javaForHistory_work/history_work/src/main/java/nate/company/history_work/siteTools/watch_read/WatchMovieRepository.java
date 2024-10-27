package nate.company.history_work.siteTools.watch_read;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * CRUD which means Create, READ, UPDATE, DELETE
 */
@Repository
public interface WatchMovieRepository extends CrudRepository<WatchMovie,Long>{
}
