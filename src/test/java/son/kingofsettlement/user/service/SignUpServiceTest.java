package son.kingofsettlement.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.repository.UserRepository;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("회원가입서비스 테스트")
class SignUpServiceTest {

	@Autowired
	private UserRepository userRepository;

	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void signUp() throws SignUpException {
		User user = User.inActiveStatusOf("myeonghee.son@gmail.com", "pass");
		User savedUser = userRepository.save(user);
		Optional<User> findUser = userRepository.findById(savedUser.getId());
		Assertions.assertEquals(user.getEmail(), findUser.get().getEmail());
	}
}