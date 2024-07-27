package son.kingofsettlement.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserUpdateRequest {

	@NotNull
	Long userId;

	@NotNull
	String nickname;
	@NotNull
	String profileUrl;
	@NotNull
	String introduction;

	public UserUpdateRequest() {
	}

	public UserUpdateRequest(String nickname, String profileUrl, String introduction) {
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.introduction = introduction;
	}
}






