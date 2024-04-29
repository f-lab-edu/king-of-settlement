package son.kingofsettlement.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

@Embeddable
@Getter
@ToString
public class UserProfile {
	private String nickname;
	private String profileUrl;
	private String introduction;
}
