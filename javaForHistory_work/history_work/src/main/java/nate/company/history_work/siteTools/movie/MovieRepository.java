package nate.company.history_work.siteTools.movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * CRUD which means Create, READ, UPDATE, DELETE
 */
@Repository
public interface MovieRepository extends CrudRepository<Movie,Long> {
}
