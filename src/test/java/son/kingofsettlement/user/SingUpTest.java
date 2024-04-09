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

// 스프링 부트 애플리케이션의 통합 테스트를 위한 어노테이션으로, 애플리케이션 컨텍스트를 로드하여 테스트하는 데 사용됨
@SpringBootTest
// 스프링 부트 테스트에서 MockMvc를 자동으로 구성하도록 지시하는 어노테이션.
// MockMvc는 스프링 MVC 애플리케이션을 테스트하는 데 사용되는 클래스로, HTTP 요청을 보내고 응답을 검증하는 데에 사용.
// 스프링 부트 애플리케이션의 컨텍스트가 로드되고 테스트 클래스에서 MockMvc 인스턴스를 직접 생성할 필요 없이
// 스프링이 자동으로 MockMvc 인스턴스를 생성해주어 해당 인스턴스를 테스트 메소드에서 사용할 수 있게 함.
@AutoConfigureMockMvc
// JUnit 5에서 사용되는 어노테이션으로, 테스트 메서드나 클래스의 이름 대신 테스트의 이름을 지정하는 데 사용
@DisplayName("회원가입")
class SignUpTest {

	// 스프링에서 의존성 주입을 수행하기 위한 어노테이션으로, 해당 필드나 메소드 파라미터에 자동으로 의존성을 주입
	@Autowired
	SignUpService signUpService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private MockMvc mockMvc;

	// JUnit에서 각각의 테스트 메소드가 실행된 이후 실행될 메소드를 지정
	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
	}

	// JUnit에서 테스트 메소드임을 나타내는 어노테이션으로, 해당 메소드가 테스트를 실행하는 메소드임을 표시
	@Test
	public void givenUser_whenUserHavingDifferentEmailJoin_thenSucceed() throws Exception {
		//given
		SignUpRequest req1 = new SignUpRequest("melody3@gmail.com", "aRs!@#!@33123df", "melody1");
		signUpService.signUp(req1);
		//when
		String requestBody = "{" + "    \"email\": \"melody1@gmail.com\"," + "    \"password\": \"aSs1132d!@#f\","
			+ "    \"nickname\": \"melody1\"" + "}";
		//then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
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
		String requestBody = "{" + "    \"email\": \"melody3@gmail.com\"," + "    \"password\": \"aSs11345d!@#f\","
			+ "    \"nickname\": \"melody2\"" + "}";
		//then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("중복된 이메일입니다."))
			.andReturn();
	}

	@Test
	public void givenInvalidEmail_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		String requestBody = "{" + "    \"email\": \"Melodnate.co.kr\"," + "    \"password\": \"14D5w2@3123\","
			+ "    \"nickname\": \"닉넴\"" + "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("이메일 형식이 잘못되었습니다."))
			.andReturn();
	}

	@Test
	public void givenPasswordLengthLessThanEight_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		String requestBody =
			"{" + "    \"email\": \"Melod5@nate.co.kr\"," + "    \"password\": \"145\"," + "    \"nickname\": \"닉넴\""
				+ "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("8자 이상 16자 이하의 비밀번호를 입력해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordLengthMoreThanSixTeen_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		String requestBody = "{" + "    \"email\": \"Melod5@nate.co.kr\"," + "    \"password\": \"12345678901234567\","
			+ "    \"nickname\": \"닉넴\"" + "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("8자 이상 16자 이하의 비밀번호를 입력해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordInvolveSpace_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		String requestBody = "{" + "    \"email\": \"Melod5@nate.co.kr\"," + "    \"password\": \"1412 31\","
			+ "    \"nickname\": \"닉넴\"" + "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 공백을 포함할수 없습니다."))
			.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveCapitalLetter_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		String requestBody = "{" + "    \"email\": \"Melod5@nate.co.kr\"," + "    \"password\": \"145w2@3123\","
			+ "    \"nickname\": \"닉넴\"" + "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 대문자를 포함해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveLowerCaseLetter_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		String requestBody = "{" + "    \"email\": \"Melod5@nate.co.kr\"," + "    \"password\": \"145W2@3123\","
			+ "    \"nickname\": \"닉넴\"" + "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 소문자를 포함해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveDigits_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		String requestBody = "{" + "    \"email\": \"Melod5@nate.co.kr\"," + "    \"password\": \"sdf!dfWs@@@\","
			+ "    \"nickname\": \"닉넴\"" + "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 숫자를 포함해주세요"))
			.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveSpecialCharacter_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		String requestBody = "{" + "    \"email\": \"5Melod@nate.co.kr\"," + "    \"password\": \"sdfdf45645Ws\","
			+ "    \"nickname\": \"닉넴\"" + "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("패스워드에 특수문자를 포함해주세요"))
			.andReturn();
	}

	@Test
	public void givenInvalidNickname_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		String requestBody = "{" + "    \"email\": \"kind@nate.co.kr\"," + "    \"password\": \"s34d!!GG645Ws\","
			+ "    \"nickname\": \"닉\"" + "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("닉네임을 확인해주세요"))
			.andReturn();
	}

	@Test
	public void givenValidNickname_whenJoinSuccess() throws Exception {
		//given
		String requestBody = "{" + "    \"email\": \"kind@nate.co.kr\"," + "    \"password\": \"s34d!!GG645Ws\","
			+ "    \"nickname\": \"123닉\"" + "}";
		//when, then
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.message").value("SignUp Succeed"))
			.andReturn();
	}
}