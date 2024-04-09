package son.kingofsettlement.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignUpService {

	private final UserRepository userRepository;

	public User signUp(SignUpRequest req) throws SignUpException {
		User user = new User(req.getEmail(), req.getPassword(), req.getNickname());
		if (isDuplicatedUser(user.getEmail())) {
			throw new SignUpException("중복된 이메일입니다.");
		}
		return userRepository.save(user);
	}

	public boolean isDuplicatedUser(String email) {
		return userRepository.findOneByEmail(email) != null;
	}
}
