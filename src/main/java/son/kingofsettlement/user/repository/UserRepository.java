package son.kingofsettlement.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import son.kingofsettlement.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findOneByEmail(String email);

	Optional<User> findOneById(Long userId);
}
