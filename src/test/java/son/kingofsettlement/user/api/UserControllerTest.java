package son.kingofsettlement.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import son.kingofsettlement.common.statusCode.UserStatusCode;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.UserException;
import son.kingofsettlement.user.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Spring 애플리케이션 전체를 로드할 필요없이 특정한 컨트롤러에 관련된 빈들만 로드하므로 효율적이며, 테스트 속도가 빠르다.
@WebMvcTest(UserController.class)
// 테스트 메소드의 이름을 지정
@DisplayName("회원가입컨트롤러 테스트")
class UserControllerTest {

	Map<String, Object> jsonMap;
	/*
		@Autowired :
		Spring 프레임워크에서 제공하는 어노테이션으로, 스프링의 의존성 주입(Dependency Injection)을 위한 것.
		스프링이 빈을 생성하고 관리하는 컴포넌트들 간의 의존성을 자동으로 주입.
		주로 실제로 동작하는 빈을 주입할 때 사용
	*/
	@Autowired
	private MockMvc mockMvc;
	/*
		@SpyBean :
		Spring Boot 테스트에서 사용되는 어노테이션으로, 실제 빈 객체를 생성하여 테스트 중에 주입할 때 사용.
		목 객체(Mock)와 유사하지만, 실제 객체의 일부 기능을 유지한 상태로 사용.
		즉, 실제 객체의 메서드 호출은 실제 객체의 구현을 따르지만, 특정한 동작을 가로챌 수 있음.
		이를 통해 실제 객체의 일부 동작을 변경하거나 특정한 상황을 시뮬레이션할 수 있다.
	 */
	@SpyBean
	private ObjectMapper objectMapper;
	/*
		@MockBea :
		Spring Boot 테스트할 때 사용되는 어노테이션, 목 객체(Mock Object)를 생성하고 주입하는데 사용.
		특정 컴포넌트가 다른 컴포넌트에 의존하고 있을 때, 외부 의존성을 목 객체로 대체하는 데 사용되는 Spring의 어노테이션.
		Spring 애플리케이션 컨텍스트에 가짜(Mock) 빈 객체를 주입.
		Spring의 의존성 주입(Dependency Injection)을 사용하는 통합 테스트(Integration Test)나
		인스턴스화된 컴포넌트를 목 객체로 대체하여 단위 테스트(Unit Test)를 수행할 때 활용.
	 */
	@MockBean
	private UserService userService;

	@BeforeEach
	public void createSignUpJson() {
		jsonMap = new HashMap<>();
		jsonMap.put("email", "melody1@gmail.com");
		jsonMap.put("password", "aSs1132d!@#f");
		jsonMap.put("nickname", "melody1");
	}

	@Test
	void testSignUp_Success() throws Exception {
		//given
		User user = User.of("melody1@gmail.com", "aRs!@#!@33123df");
		SignUpRequest req = new SignUpRequest(user.getEmail(), user.getPassword());
		//when
		when(userService.signUp(any(SignUpRequest.class))).thenReturn(user);
		//then
		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	void testSignUp_Fail() throws Exception {
		//given
		User user = User.of("melody1@gmail.com", "aRs!@#!@33123df");
		SignUpRequest req = new SignUpRequest(user.getEmail(), user.getPassword());
		//when
		doThrow(new UserException(UserStatusCode.DUPLICATED_USER)).when(userService).signUp(any(SignUpRequest.class));
		//then
		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isBadRequest());
	}


	// JUnit에서 테스트 메소드임을 나타내는 어노테이션으로, 해당 메소드가 테스트를 실행하는 메소드임을 표시
	@Test
	public void givenUser_whenUserHavingDifferentEmailJoin_thenSucceed() throws Exception {
		//given
		String email1 = "melody1@gmail.com";
		String email2 = "melody3@gmail.com";
		SignUpRequest req1 = new SignUpRequest(email1, "aRs!@#!@33123df");
		userService.signUp(req1);
		//when
		jsonMap.put("email", email2);
		//then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.result.code").value(1000))
				.andReturn();
	}

	@Test
	public void givenUser_whenUserHavingSameEmailJoin_thenThrowSinUpException() throws Exception {
		//given
		String email1 = "melody1@gmail.com";
		SignUpRequest req1 = new SignUpRequest(email1, "aRs!@#!@33123df");
		userService.signUp(req1);

		//when
		jsonMap.put("email", email1);
		doThrow(new UserException(UserStatusCode.DUPLICATED_USER)).when(userService).signUp(any(SignUpRequest.class));
		//then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result.message").value("중복된 이메일입니다."))
				.andReturn();
	}

	@Test
	public void givenInvalidEmail_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		jsonMap.put("email", "melody1gmail.com");
		//when, then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result.message").value("이메일 형식이 잘못되었습니다."))
				.andReturn();
	}

	@Test
	public void givenPasswordLengthLessThanEight_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		jsonMap.put("password", "124");
		//when, then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result.message").value("비밀번호를 확인해주세요"))
				.andReturn();
	}

	@Test
	public void givenPasswordLengthMoreThanSixTeen_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		jsonMap.put("password", "12345sdfsdf45645!!901234567");
		//when, then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result.message").value("비밀번호를 확인해주세요"))
				.andReturn();
	}

	@Test
	public void givenPasswordInvolveSpace_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		jsonMap.put("password", "1sdf !!dasdD");
		//when, then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result.message").value("비밀번호를 확인해주세요"))
				.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveCapitalLetter_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		jsonMap.put("password", "145w2@3123");
		//when, then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result.message").value("비밀번호를 확인해주세요"))
				.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveLowerCaseLetter_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		jsonMap.put("password", "145W2@3123");
		//when, then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result.message").value("비밀번호를 확인해주세요"))
				.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveDigits_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		jsonMap.put("password", "sdf!dfWs@@@");
		//when, then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result.message").value("비밀번호를 확인해주세요"))
				.andReturn();
	}

	@Test
	public void givenPasswordNotInvolveSpecialCharacter_whenJoin_thenThrowSinUpException() throws Exception {
		//given
		jsonMap.put("password", "sdfdf45645Ws");
		//when, then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.result.message").value("비밀번호를 확인해주세요"))
				.andReturn();
	}

	@Test
	public void givenValidNickname_whenJoinSuccess() throws Exception {
		//given
		jsonMap.put("nickname", "123닉");
		//when, then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.result.code").value(1000))
				.andReturn();
	}

}