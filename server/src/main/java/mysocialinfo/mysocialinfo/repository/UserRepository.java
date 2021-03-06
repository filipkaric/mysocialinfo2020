package mysocialinfo.mysocialinfo.repository;

import mysocialinfo.mysocialinfo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);

}
