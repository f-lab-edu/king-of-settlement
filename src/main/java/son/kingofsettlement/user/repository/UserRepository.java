package son.kingofsettlement.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import son.kingofsettlement.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findOneByEmail(String email);
}
