package son.kingofsettlement.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import son.kingofsettlement.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);

    Optional<User> findOneBySessionKey(String sessionKey);
}
