package son.kingofsettlement.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import son.kingofsettlement.user.dto.LogInRequest;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.exception.UserDoesNotExistException;
import son.kingofsettlement.user.repository.UserRepository;

// 해당 클래스가 비즈니스 로직을 수행하는 서비스 클래스임을 나타내는 어노테이션으로, 주로 서비스 계층의 클래스에 사용
@Service
// Lombok 라이브러리에서 제공하는 어노테이션으로, final 필드가 있는 생성자를 생성해주는 역할
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	// 트랜잭션 처리를 지원하기 위한 어노테이션으로, 해당 메소드나 클래스에 트랜잭션을 적용
	@Transactional
	public User signUp(SignUpRequest req) throws SignUpException {
		String encryptedEmail = AESEncryption.encrypt(req.getEmail());
		if (userRepository.findOneByEmail(encryptedEmail).isPresent()) {
			throw new SignUpException("중복된 이메일입니다.");
		}
		String salt = BCrypt.gensalt(10);
		String encryptedPassword = BCrypt.hashpw(req.getPassword(), salt);
		User user = User.of(encryptedEmail, encryptedPassword);
		return userRepository.save(user);
	}

	// 트랜잭션 처리를 지원하기 위한 어노테이션으로, 해당 메소드나 클래스에 트랜잭션을 적용
	@Transactional
	public User login(HttpServletRequest request, LogInRequest req) throws UserDoesNotExistException {
		String password = req.getPassword();
		String email = req.getEmail();
		User existUser = userRepository.findOneByEmail(AESEncryption.encrypt(email))
				.orElseThrow(() -> new UserDoesNotExistException("해당 유저가 존재하지 않습니다."));
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);
		session.setAttribute(SessionConst.LOGIN_MEMBER, existUser);
		if (BCrypt.checkpw(password, existUser.getPassword())) {
			existUser.updateSessionKey(session.getId());
		}
		return existUser;
	}
}
