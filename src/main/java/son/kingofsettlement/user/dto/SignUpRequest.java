package son.kingofsettlement.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpRequest {
	@NotBlank
	@Email(message = "이메일 형식이 잘못되었습니다.")
	String email;
	@Pattern(regexp = "(((?=(.*[A-Z].*){1,})(?=(.*[a-z].*){1,})(?=(.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~].*){1,})(?=(.*[0-9].*){1,})){8,15}).*",
			message = "비밀번호를 확인해주세요")
	String password;
	
	@Pattern(regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣]{2,10}$",
			message = "닉네임을 확인해주세요")
	String nickname;

	public SignUpRequest() {
	}

	public SignUpRequest(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}
}