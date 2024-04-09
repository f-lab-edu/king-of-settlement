package son.kingofsettlement.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.dto.SignUpResponse;
import son.kingofsettlement.user.service.SignUpService;

@SpringBootTest
class SignUpTest {

	@Autowired
	SignUpService signUpService;

	@Test
	public void successJoin() throws Exception {
		//given
		SignUpRequest req1 = new SignUpRequest("melody1@gmail.com", "aSs1132d!@#f", "melody1");
		signUpService.signUp(req1);
		//when
		SignUpRequest req2 = new SignUpRequest("melody2@gmail.com", "QWe12132@@r", "melody2");
		SignUpResponse response = signUpService.signUp(req2);
		//then
		Assertions.assertEquals("회원가입 성공", response.message());
	}

	@Test
	public void failJoin() throws Exception {
		//given
		SignUpRequest req1 = new SignUpRequest("melody3@gmail.com", "aRs!@#!@33123df", "melody1");
		signUpService.signUp(req1);
		//when
		SignUpRequest req2 = new SignUpRequest("melody3@gmail.com", "QWe!@#345r", "melody2");
		SignUpResponse response = signUpService.signUp(req2);
		//then
		Assertions.assertEquals("회원가입 실패 : 중복된 이메일입니다.", response.message());
	}

}