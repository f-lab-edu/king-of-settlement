package son.kingofsettlement.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.UserException;
import son.kingofsettlement.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
	@ExtendWith(MockitoExtension.class)
	: JUnit 5에서 Mockito를 사용하는 테스트 클래스에서 Mockito의 기능을 활용할 수 있도록 도와주는 확장 기능을 활성화하는 어노테이션
	테스트 클래스에 포함된 Mockito 관련 어노테이션들(@Mock, @InjectMocks, @Spy, @Captor 등)이 동작하게 되어,
	목 객체를 생성하고 설정하거나 주입하는 등의 작업을 보다 편리하게 할 수 있다.
 */
@ExtendWith(MockitoExtension.class)
// 테스트 메소드의 이름을 지정
@DisplayName("회원가입서비스 테스트")
class UserServiceTest {
	/*
		@Mock:
		Mockito 라이브러리에서 제공. 목 객체(Mock Object)를 생성할 때 사용.
		목 객체는 실제 객체를 대신하여 테스트 중에 호출되는 메서드의 동작을 가로채거나 특정한 반환값이나 예외를 설정가능
		주로 단위 테스트에서 사용된다. 아래 코드에서는 Mock된 UserRepository 주입
	 */
	@Mock
	private UserRepository userRepository;
	// Mock된 UserRepository를 주입받는 UserService 주입
	@InjectMocks
	private UserService userService;

	// JUnit 프레임워크에서 사용되는 테스트 메소드를 지정하는 어노테이션. 이 어노테이션이 지정된 메소드는 JUnit에 의해 테스트의 일부로 간주되어 실행된다.
	@Test
	void testSignUp_WithUniqueEmail() {
		// given
		User user = User.of("myeonghee.son@gmail.com", "pass");
		SignUpRequest req = new SignUpRequest(user.getEmail(), user.getPassword());
		// when
		when(userRepository.save(any(User.class))).thenReturn(user);
		User registeredUser = userService.signUp(req);
		// then
		assertEquals(user.getEmail(), registeredUser.getEmail());
		assertEquals(user.getPassword(), registeredUser.getPassword());
	}

	@Test
	public void testSignUp_WithDuplicateEmail() {
		//given
		User user = User.of("myeonghee.son1@gmail.com", "pass");
		SignUpRequest req = new SignUpRequest(user.getEmail(), user.getPassword());
//		userService.signUp(req);
		//when
		when(userRepository.findOneByEmail(AESEncryption.encrypt(user.getEmail()))).thenReturn(Optional.of(user));
		//then
		assertThrows(UserException.class, () -> userService.signUp(req));
	}
}