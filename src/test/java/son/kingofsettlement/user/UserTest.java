package son.kingofsettlement.user;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.entity.UserFactory;
import son.kingofsettlement.user.repository.UserRepository;

// 스프링 부트 애플리케이션의 통합 테스트를 위한 어노테이션으로, 애플리케이션 컨텍스트를 로드하여 테스트하는 데 사용됨
@SpringBootTest
// JUnit 5에서 사용되는 어노테이션으로, 테스트 메서드나 클래스의 이름 대신 테스트의 이름을 지정하는 데 사용
@DisplayName("사용자")
class UserTest {
	// 스프링에서 의존성 주입을 수행하기 위한 어노테이션으로, 해당 필드나 메소드 파라미터에 자동으로 의존성을 주입
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserFactory userFactory;

	// JUnit에서 각각의 테스트 메소드가 실행된 이후 실행될 메소드를 지정
	@AfterEach
	public void tearDown() {
		userRepository.deleteAll();
	}

	// JUnit에서 테스트 메소드임을 나타내는 어노테이션으로, 해당 메소드가 테스트를 실행하는 메소드임을 표시
	@Test
	void givenUser_whenSave_thenInputUserEqualsToOutputUser() {
		// given
		User user1 = userFactory.createUser("myeonghee.son@gmail.com", "pass");
		// when
		User member1 = userRepository.save(user1);
		// then
		Assertions.assertEquals(user1, member1);
	}

	@Test
	void givenSaveUser_whenFindUserBySameEmail_thenFindUserIdEqualsToSavedUserId() throws Exception {
		// given
		User savedUser = userRepository.save(userFactory.createUser("myeonghee.son@gmail.com", "pass"));
		// when
		Optional<User> findeduser = userRepository.findOneByEmail("myeonghee.son@gmail.com");
		// then
		Assertions.assertEquals(findeduser.get().getId(), savedUser.getId());
	}
}
