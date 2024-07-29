package son.kingofsettlement.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Spring 애플리케이션 전체를 로드할 필요없이 특정한 컨트롤러에 관련된 빈들만 로드하므로 효율적이며, 테스트 속도가 빠르다.
@WebMvcTest(UserController.class)
// 테스트 메소드의 이름을 지정
@DisplayName("회원가입컨트롤러 테스트")
class UserControllerTest {

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
		doThrow(new SignUpException("중복된 이메일입니다.")).when(userService).signUp(any(SignUpRequest.class));
		//then
		mockMvc.perform(post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isBadRequest());
	}
}