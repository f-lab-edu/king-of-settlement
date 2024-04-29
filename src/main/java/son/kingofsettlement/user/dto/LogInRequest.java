package son.kingofsettlement.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LogInRequest {
	@Email(message = "이메일 형식이 잘못되었습니다.") String email;
	@Pattern(regexp = "^(?=.*[A-Z]).(?=.*[a-z]).(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~]).(?=.*[0-9]).{8,15}$", message = "비밀번호를 확인해주세요") String password;

	public LogInRequest() {
	}

	public LogInRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
