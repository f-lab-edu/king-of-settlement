package son.kingofsettlement.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.entity.UserFactory;
import son.kingofsettlement.user.exception.EncryptException;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.repository.UserRepository;

// 해당 클래스가 비즈니스 로직을 수행하는 서비스 클래스임을 나타내는 어노테이션으로, 주로 서비스 계층의 클래스에 사용
@Service
// Lombok 라이브러리에서 제공하는 어노테이션으로, final 필드가 있는 생성자를 생성해주는 역할
@RequiredArgsConstructor
// 트랜잭션 처리를 지원하기 위한 어노테이션으로, 해당 메소드나 클래스에 트랜잭션을 적용
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final UserFactory userFactory;

	public User signUp(SignUpRequest req) throws EncryptException, SignUpException {
		isDuplicatedUser(req.getEmail());
		PasswordEncoder passwordEncoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
		String encryptedEmail = AESEncryption.encrypt(req.getEmail());
		String hashedPassword = passwordEncoder.encode(req.getPassword());
		User user = userFactory.createUser(encryptedEmail, hashedPassword);
		return userRepository.save(user);
	}

	public void isDuplicatedUser(String email) throws SignUpException {
		if (userRepository.findOneByEmail(email) != null) {
			throw new SignUpException("중복된 이메일입니다.");
		}
	}
}
