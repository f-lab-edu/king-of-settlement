package son.kingofsettlement.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import son.kingofsettlement.user.repository.UserRepository;
import son.kingofsettlement.user.service.SignUpService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("회원가입컨트롤러 테스트")
class UserControllerTest {
	@Autowired
	SignUpService signUpService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void signUp() throws Exception {
		//given
		String email = "melody1@gmail.com";
		String password = "aRs!@#!@33123df";
		//when
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("email", email);
		jsonMap.put("password", password);
		//then
		MvcResult mvcResult = mockMvc.perform(
						MockMvcRequestBuilders.post("/users")
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(jsonMap)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value("SignUp Succeed"))
				.andReturn();
	}
}