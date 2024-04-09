package son.kingofsettlement.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.repository.UserRepository;

@SpringBootTest
@DisplayName("사용자")
class UserTest {

	@Autowired
	UserRepository userRepository;

	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void givenUser_whenSave_thenInputUserEqualsToOutputUser() {
		// given
		User user1 = new User("myeonghee.son@gmail.com", "pass", "user1");
		// when
		User member1 = userRepository.save(user1);
		// then
		Assertions.assertEquals(user1, member1);
	}

	@Test
	void givenSaveUser_whenFindUserBySameEmail_thenFindUserIdEqualsToSavedUserId() throws Exception {
		// given
		User savedUser = userRepository.save(new User("myeonghee.son@gmail.com", "pass", "user1"));
		// when
		User findeduser = userRepository.findOneByEmail("myeonghee.son@gmail.com");
		// then
		Assertions.assertEquals(findeduser.getId(), savedUser.getId());
	}
}
