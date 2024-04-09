package son.kingofsettlement.user;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.repository.UserRepository;
import son.kingofsettlement.user.service.SignUpService;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("회원가입")
class SignUpTest {

	@Autowired
	SignUpService signUpService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	public void givenUser_whenUserHavingDifferentEmailJoin_then() throws Exception {
		//given
		SignUpRequest req1 = new SignUpRequest("melody3@gmail.com", "aRs!@#!@33123df", "melody1");
		signUpService.signUp(req1);
		//when
		String requestBody = "{"
			+ "    \"email\": \"melody1@gmail.com\","
			+ "    \"password\": \"aSs1132d!@#f\","
			+ "    \"nickname\": \"melody1\""
			+ "}";
		//then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value("SignUp Succeed"))
			.andReturn();
	}

	@Test
	public void givenUser_whenUserHavingSameEmailJoin_thenThrowSinUpException() throws Exception {
		//given
		SignUpRequest req1 = new SignUpRequest("melody3@gmail.com", "aRs!@#!@33123df", "melody1");
		signUpService.signUp(req1);

		//when
		String requestBody = "{"
			+ "    \"email\": \"melody3@gmail.com\","
			+ "    \"password\": \"aSs11345d!@#f\","
			+ "    \"nickname\": \"melody2\""
			+ "}";
		//then
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("중복된 이메일입니다."))
			.andReturn();
	}

	@Test
	public void givenInvalidEmail_whenJoin_thenThrowSinUpException() throws Exception {
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
	public void givenPasswordLengthLessThanEight_whenJoin_thenThrowSinUpException() throws Exception {
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
	public void givenPasswordLengthMoreThanSixTeen_whenJoin_thenThrowSinUpException() throws
		Exception {
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
	public void givenPasswordInvolveSpace_whenJoin_thenThrowSinUpException() throws Exception {
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
	public void givenPasswordNotInvolveCapitalLetter_whenJoin_thenThrowSinUpException() throws
		Exception {
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
	public void givenPasswordNotInvolveLowerCaseLetter_whenJoin_thenThrowSinUpException() throws
		Exception {
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
	public void givenPasswordNotInvolveDigits_whenJoin_thenThrowSinUpException() throws Exception {
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
	public void givenPasswordNotInvolveSpecialCharacter_whenJoin_thenThrowSinUpException() throws
		Exception {
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
	public void givenInvalidNickname_whenJoin_thenThrowSinUpException() throws Exception {
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
	public void givenValidNickname_whenJoinSuccess() throws Exception {
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