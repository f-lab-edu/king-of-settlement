package son.kingofsettlement.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserUpdateRequest {

	@NotBlank
	String nickname;
	@NotBlank
	String profileUrl;
	@NotBlank
	String introduction;

	public UserUpdateRequest() {
	}

	public UserUpdateRequest(String nickname, String profileUrl, String introduction) {
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.introduction = introduction;
	}
}






