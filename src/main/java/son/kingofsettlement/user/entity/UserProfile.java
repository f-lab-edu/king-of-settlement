package son.kingofsettlement.user.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserProfile {
	private String nickname;
	private String profileUrl;
	private String introduction;
}
