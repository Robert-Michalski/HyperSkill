package antifraud.repository;

import antifraud.model.User.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);
    int deleteByUsername(String username);

    User findByUsername(String username);
}
