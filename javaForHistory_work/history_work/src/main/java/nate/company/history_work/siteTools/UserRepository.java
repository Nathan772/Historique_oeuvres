package nate.company.youtube_converter.siteTools;

import nate.company.youtube_converter.controller.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * interface créée manuellement.
 * Elle est nécessaire pour pouvoir utiliser les méthodes associées à la BDD,
 * CRUD, c'est à dire : Create, READ, UPDATE, DELETE
 */
@Repository
public interface UserRepository extends CrudRepository<User,Long> {


}
