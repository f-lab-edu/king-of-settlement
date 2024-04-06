package son.kingofsettlement.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import son.kingofsettlement.user.dto.SignUpResponse;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignUpService {

	private final UserRepository userRepository;

	public SignUpResponse join(User user) {

		if (isDuplicatedUser(user.getUserEmail())) {
			return new SignUpResponse("회원가입 실패 : 중복된 이메일입니다.");
		}
		userRepository.save(user);
		return new SignUpResponse("회원가입 성공");
	}

	public boolean isDuplicatedUser(String email) {
		return userRepository.findOneByUserEmail(email) != null;
	}

}
