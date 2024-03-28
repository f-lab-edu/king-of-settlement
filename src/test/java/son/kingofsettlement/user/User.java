package son.kingofsettlement.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import son.kingofsettlement.user.domain.User;
import son.kingofsettlement.user.repository.UserRepository;
import son.kingofsettlement.user.service.UserService;

@SpringBootTest
class UserTest {

	@Autowired
	EntityManager em;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Test
	void save() {
		User user1 = new User("Melody");
		User member1 = userService.join(user1);
		System.out.println(member1);
		Assertions.assertEquals(user1, member1);
		// return member1.getId();
	}
}
