package nate.company.history_work.siteTools.user;
import nate.company.history_work.siteTools.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * CRUD which means Create, READ, UPDATE, DELETE
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    User save(User user);

    Optional<User> findByPseudo(String pseudo);

    Optional<User> findByEmail(String email);

    boolean existsByPseudo(String pseudo);

    Optional<User> getUserById(long id);

    void removeByPseudo(String pseudo);

    //List<User> getAll();
}

