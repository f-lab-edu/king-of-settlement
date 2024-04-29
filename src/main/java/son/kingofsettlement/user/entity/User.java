package son.kingofsettlement.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import son.kingofsettlement.user.dto.UserStatus;

import java.time.LocalDateTime;

// JPA 엔티티 클래스임을 나타내는 어노테이션으로, 데이터베이스의 테이블과 매핑되는 클래스임을 표시
@Entity
// Lombok 라이브러리에서 제공하는 어노테이션으로, 클래스의 필드에 대한 getter 메소드를 자동으로 생성
@Getter
public class User {
	// JPA 엔티티 클래스에서 주(primary) 식별자 필드를 지정하는 어노테이션으로, 해당 필드가 엔티티의 주키임을 나타냄
	@Id
	// 주(primary) 키 값이 자동으로 생성되는 방식을 설정하는 어노테이션으로, 주로 자동으로 증가하는 식별자를 생성할 때 사용
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 엔티티 클래스의 필드와 데이터베이스 테이블의 컬럼을 매핑하는데 사용되며,  name, nullable, unique 등의 속성을 가지고 있어, 데이터베이스 컬럼의 속성을 정의
	@Column(name = "user_id")
	private Long id;
	@Column(name = "encrypted_email")
	private String email;
	@Column(name = "hashed_password")
	private String password;
	@Column(name = "session_id")
	private String sessionId;
	@Column(name = "nickname")
	private String nickname;
	@Column(name = "profile_url")
	private String profileUrl;
	@Column(name = "introduction")
	private String introduction;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private UserStatus activityStatus;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
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
