package son.kingofsettlement.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import son.kingofsettlement.user.dto.UserStatus;

@Entity
@Getter
@Table
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	private String email;
	private String password;
	private String sessionId;
	private String nickname;
	private String profileUrl;
	private String introduction;
	private UserStatus activityStatus;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public User() {
	}

	public User(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.activityStatus = UserStatus.INACTIVE;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
}
