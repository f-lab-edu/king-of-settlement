package son.kingofsettlement.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.ToString;

// JPA에서 사용되는 어노테이션으로, 엔티티에 포함될 값 객체를 정의하는 데 사용
@Embeddable
// Lombok 라이브러리에서 제공되는 어노테이션으로, 클래스의 필드에 대한 getter 메서드를 자동으로 생성해주는 기능을 제공
@Getter
// Lombok 라이브러리에서 제공되는 어노테이션으로, 클래스의 toString() 메서드를 자동으로 생성해주는 기능을 제공.
@ToString
public class UserProfile {
	private String nickname;
	private String profileUrl;
	private String introduction;

	public UserProfile() {
	}

	public UserProfile(String nickname, String profileUrl, String introduction) {
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.introduction = introduction;
	}

	public static UserProfile of(String nickname, String profileUrl, String introduction) {
		return new UserProfile(nickname, profileUrl, introduction);
	}
}
