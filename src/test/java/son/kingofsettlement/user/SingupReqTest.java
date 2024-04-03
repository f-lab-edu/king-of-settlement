package son.kingofsettlement.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.service.SignUpService;

@WebMvcTest(SignUpException.class)
class SignUpReqTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SignUpService service;

	@Test
	@DisplayName("회원가입실패 : 이메일 형식 오류")
	void shouldReturnEmailInvalidMessage() throws Exception {
		Assertions.assertThrows(SignUpException.class, () -> {
			new SignUpRequest("melody123", "asdfasdf", "Asdf");
		});
	}
}