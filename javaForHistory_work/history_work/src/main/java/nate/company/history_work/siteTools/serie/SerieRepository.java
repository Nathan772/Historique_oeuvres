package nate.company.history_work.siteTools.serie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * CRUD which means Create, READ, UPDATE, DELETE
 */
@Repository
public interface SerieRepository extends CrudRepository<Serie,Long> {
}
