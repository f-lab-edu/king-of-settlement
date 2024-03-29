package son.kingofsettlement.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import son.kingofsettlement.user.exception.entity.User;
import son.kingofsettlement.user.repository.UserRepository;

@SpringBootTest
class UserTest {

	@Autowired
	UserRepository userRepository;

	@Test
	void save() {
		// given
		User user1 = new User("myeonghee.son@gmail.com");
		// when
		User member1 = userRepository.save(user1);
		// then
		Assertions.assertEquals(user1, member1);
	}
}
