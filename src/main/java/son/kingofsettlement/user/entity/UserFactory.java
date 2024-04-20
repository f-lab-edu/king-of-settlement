package son.kingofsettlement.user.entity;

import org.springframework.stereotype.Component;

@Component
public class UserFactory {
	public User createUser(String email, String password) {
		return new User(email, password);
	}
}
