package son.kingofsettlement.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpRequest {

	// "", null 둘다
	@NotBlank
	@Email(message = "Invalid email")
	String email;

	@NotBlank
	@Pattern(regexp = "^(?=.*[A-Z]).(?=.*[a-z]).(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[\\]^_`{|}~]).(?=.*[0-9]).{8,15}$", message = "Invalid password")
	String password;

	public SignUpRequest() {
	}

	public SignUpRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}