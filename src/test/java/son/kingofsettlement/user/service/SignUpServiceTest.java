package son.kingofsettlement.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
//@AutoConfigureMockMvc
@DisplayName("회원가입서비스 테스트")
class SignUpServiceTest {
	// 가짜 UserRepository 생성 및 의존성 주입
	private final UserRepository userRepository = mock(UserRepository.class);
	// UserService 생성 및 가짜 UserRepository 의존성 주입
	private final SignUpService signUpService = new SignUpService(userRepository);

	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
	}

	// JUnit 프레임워크에서 사용되는 테스트 메소드를 지정하는 어노테이션. 이 어노테이션이 지정된 메소드는 JUnit에 의해 테스트의 일부로 간주되어 실행된다.
	@Test
	void signUp() throws SignUpException {
		// 테스트할 유저 정보 생성
		User user = User.inActiveStatusOf("myeonghee.son@gmail.com", "pass");

		// 테스트할 요청 정보 생성
		SignUpRequest req = new SignUpRequest(user.getEmail(), user.getPassword(), user.getNickname());

		// UserRepository의 save 메소드가 호출될 때 반환할 값을 지정
		when(userRepository.save(any(User.class))).thenReturn(user);

		// UserService의 signUp 메소드 호출
		User registeredUser = signUpService.signUp(req);

		assertEquals(user.getEmail(), registeredUser.getEmail());
		assertEquals(user.getPassword(), registeredUser.getPassword());
	}
}