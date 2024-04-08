package son.kingofsettlement.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.repository.UserRepository;

@SpringBootTest
class UserTest {

	@Autowired
	UserRepository userRepository;

	@Test
	void save() {
		// given
		User user1 = new User("myeonghee.son@gmail.com", "pass", "user1");
		// when
		User member1 = userRepository.save(user1);
		// then
		Assertions.assertEquals(user1, member1);
	}

	@Test
	void checkEmailDuplication() throws Exception {
		// given
		userRepository.save(new User("myeonghee.son@gmail.com", "pass", "user1"));
		// when
		User user = userRepository.findOneByEmail("myeonghee.son@gmail.com");
		// then
		Assertions.assertEquals(user.getId(), 1L);
	}
}
