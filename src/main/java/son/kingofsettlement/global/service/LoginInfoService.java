package son.kingofsettlement.global.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.UserDoseNotExist;
import son.kingofsettlement.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginInfoService {
	private final UserRepository userRepository;

	public Optional<User> getLoginUser(HttpServletRequest request) throws UserDoseNotExist {
		Cookie[] cookies = request.getCookies();
		String jsessionid = "";
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("JSESSIONID")) {
					jsessionid = cookies[i].getValue();
					break;
				}
			}
		}
		return userRepository.findOneBySessionId(jsessionid);
	}
}
