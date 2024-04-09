package son.kingofsettlement.user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.service.SignUpService;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpTest {

	@Autowired
	SignUpService signUpService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void successJoin() throws Exception {
		//given
		SignUpRequest req1 = new SignUpRequest("melody1@gmail.com", "aSs1132d!@#f", "melody1");
		signUpService.signUp(req1);
		//when
		SignUpRequest req2 = new SignUpRequest("melody2@gmail.com", "QWe12132@@r", "melody2");
		User user = signUpService.signUp(req2);
		//then
		Assertions.assertEquals("melody2", user.getNickname());
	}

	@Test
	public void failJoin() throws Exception {
		//given
		SignUpRequest req1 = new SignUpRequest("melody3@gmail.com", "aRs!@#!@33123df", "melody1");
		signUpService.signUp(req1);
		//when
		SignUpRequest req2 = new SignUpRequest("melody3@gmail.com", "QWe!@#345r", "melody2");
		//then
		SignUpException e = Assertions.assertThrows(SignUpException.class,
			() -> signUpService.signUp(req2));
		Assertions.assertEquals("중복된 이메일입니다.", e.getMessage());
	}

	@Test
	public void givenInvalidEmailWhenJoinThrowSinUpException() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"Melodnate.co.kr\","
			+ "    \"password\": \"14D5w2@3123\","
			+ "    \"nickname\": \"닉넴\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("이메일 형식이 잘못되었습니다."))
			.andReturn();
	}

	@Test
	public void givenPasswordLengthLessThanEightWhenJoinThrowSinUpException() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"Melod5@nate.co.kr\","
			+ "    \"password\": \"145\","
			+ "    \"nickname\": \"닉넴\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("8자 이상 16자 이하의 비밀번호를 입력해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordLengthMoreThanSixTeenWhenJoinThrowSinUpException() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"Melod5@nate.co.kr\","
			+ "    \"password\": \"12345678901234567\","
			+ "    \"nickname\": \"닉넴\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("8자 이상 16자 이하의 비밀번호를 입력해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordInvolveSpaceWhenJoinThrowSinUpException() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"Melod5@nate.co.kr\","
			+ "    \"password\": \"1412 31\","
			+ "    \"nickname\": \"닉넴\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 공백을 포함할수 없습니다."))
			.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveCapitalLetterWhenJoinThrowSinUpException() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"Melod5@nate.co.kr\","
			+ "    \"password\": \"145w2@3123\","
			+ "    \"nickname\": \"닉넴\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 대문자를 포함해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveLowerCaseLetterWhenJoinThrowSinUpException() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"Melod5@nate.co.kr\","
			+ "    \"password\": \"145W2@3123\","
			+ "    \"nickname\": \"닉넴\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 소문자를 포함해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveDigitsWhenJoinThrowSinUpException() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"Melod5@nate.co.kr\","
			+ "    \"password\": \"sdf!dfWs@@@\","
			+ "    \"nickname\": \"닉넴\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 숫자를 포함해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveSpecialCharacterWhenJoinThrowSinUpException() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"5Melod@nate.co.kr\","
			+ "    \"password\": \"sdfdf45645Ws\","
			+ "    \"nickname\": \"닉넴\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 특수문자를 포함해주세요"))
			.andReturn();
	}

	@Test
	public void givenInvalidNicknameWhenJoinThrowSinUpException() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"kind@nate.co.kr\","
			+ "    \"password\": \"s34d!!GG645Ws\","
			+ "    \"nickname\": \"닉\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("닉네임을 확인해주세요"))
			.andReturn();
	}

	@Test
	public void givenValidNicknameWhenJoinSuccess() throws Exception {
		//given
		String requestBody = "{"
			+ "    \"email\": \"kind@nate.co.kr\","
			+ "    \"password\": \"s34d!!GG645Ws\","
			+ "    \"nickname\": \"123닉\""
			+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value("SignUp Succeed"))
			.andReturn();
	}
}