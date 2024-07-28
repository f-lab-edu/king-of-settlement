package son.kingofsettlement.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import son.kingofsettlement.common.statusCode.UserStatusCode;
import son.kingofsettlement.user.dto.LogInRequest;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.dto.UserUpdateRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.entity.UserProfile;
import son.kingofsettlement.user.exception.UserException;
import son.kingofsettlement.user.repository.UserRepository;

// 해당 클래스가 비즈니스 로직을 수행하는 서비스 클래스임을 나타내는 어노테이션으로, 주로 서비스 계층의 클래스에 사용
@Service
// Lombok 라이브러리에서 제공하는 어노테이션으로, final 필드가 있는 생성자를 생성해주는 역할
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	// 트랜잭션 처리를 지원하기 위한 어노테이션으로, 해당 메소드나 클래스에 트랜잭션을 적용
	@Transactional
	public User signUp(SignUpRequest req) throws UserException {
		String encryptedEmail = AESEncryption.encrypt(req.getEmail());
		if (userRepository.findOneByEmail(encryptedEmail).isPresent()) {
			throw new UserException(UserStatusCode.DUPLICATED_USER);
		}
		String salt = BCrypt.gensalt(10);
		String encryptedPassword = BCrypt.hashpw(req.getPassword(), salt);
		User user = User.of(encryptedEmail, encryptedPassword);
		return userRepository.save(user);
	}

	// 트랜잭션 처리를 지원하기 위한 어노테이션으로, 해당 메소드나 클래스에 트랜잭션을 적용
	@Transactional
	public User login(HttpServletRequest request, LogInRequest req) throws UserException {
		String password = req.getPassword();
		String email = req.getEmail();
		User existUser = userRepository.findOneByEmail(AESEncryption.encrypt(email))
			.orElseThrow(() -> new UserException(UserStatusCode.USER_NOT_FOUND));
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);
		session.setAttribute(SessionConst.LOGIN_MEMBER, existUser);
		if (BCrypt.checkpw(password, existUser.getPassword())) {
			existUser.updateSessionKey(session.getId());
		}
		return existUser;
	}

	@Transactional
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}

	public User updateProfile(Long userId, UserUpdateRequest req) {
		Optional<User> findUser = userRepository.findOneById(userId);
		if (findUser.isEmpty()) {
			throw new UserException(UserStatusCode.USER_NOT_FOUND);
		}
		User user = findUser.get();
		UserProfile profile = UserProfile.of(req.getNickname(), req.getProfileUrl(), req.getIntroduction());
		user.updateProfile(profile);
		return userRepository.save(user);
	}

	public Long delete(Long userId) {
		Optional<User> findUser = userRepository.findOneById(userId);
		if (findUser.isEmpty()) {
			throw new UserException(UserStatusCode.USER_NOT_FOUND);
		}
		User user = findUser.get();
		return user.delete();
	}

}
