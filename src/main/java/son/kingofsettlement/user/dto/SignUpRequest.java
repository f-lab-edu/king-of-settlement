package son.kingofsettlement.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpRequest {
	@Pattern(regexp = "[a-z._\\-A-Z0-9]+@[a-z._\\-A-Z0-9]+[.][a-z._\\-A-Z0-9]+", message = "이메일 형식이 잘못되었습니다.")
	String email;
	String password;
	String nickname;

	public SignUpRequest() {
	}

	public SignUpRequest(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}
}