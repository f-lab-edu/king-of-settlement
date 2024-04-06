package son.kingofsettlement.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import son.kingofsettlement.user.dto.SignUpResponse;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.service.SignUpService;

@SpringBootTest
class SignUpTest {

	@Autowired
	SignUpService signUpService;

	@Test
	public void successJoin() throws Exception {
		//given
		User user = new User("melody@gmail.com", "asdf", "melody");
		//when
		SignUpResponse response = signUpService.signUp(user);
		//then
		Assertions.assertEquals("회원가입 성공", response.message());
	}

	@Test
	public void failJoin() throws Exception {
		//given
		User user1 = new User("melody@gmail.com", "asdf", "melody1");
		User user2 = new User("melody@gmail.com", "123", "melody2");
		signUpService.signUp(user1);
		//when
		SignUpResponse response = signUpService.signUp(user2);
		//then
		Assertions.assertEquals("회원가입 실패 : 중복된 이메일입니다.", response.message());
	}

}