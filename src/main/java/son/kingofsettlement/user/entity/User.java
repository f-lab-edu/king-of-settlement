package son.kingofsettlement.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import son.kingofsettlement.user.dto.UserStatus;
import son.kingofsettlement.user.service.AESEncryption;

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
    @Column(name = "id")
    private Long id;
    @Column(name = "encrypted_email", unique = true)
    private String email;
    // Jackson 라이브러리에서 JSON 직렬화 및 역직렬화 과정에서 특정 필드를 무시하도록 지정하는 데 사용되는 어노테이션
    @JsonIgnore
    @Column(name = "hashed_password")
    private String password;
    // Jackson 라이브러리에서 JSON 직렬화 및 역직렬화 과정에서 특정 필드를 무시하도록 지정하는 데 사용되는 어노테이션
    @JsonIgnore
    @Column(name = "session_key")
    private String sessionKey;
    /*
        @Embeddable :
            JPA(Java Persistence API)에서 사용되는 어노테이션으로, 엔티티에 포함될 값 객체를 정의하는 데 사용됩니다.
            이 어노테이션을 사용하여 값 객체를 정의하면, 해당 값 객체는 엔티티의 일부로서 해당 테이블에 직접 매핑됩니다.
     */
    @Embedded
    private UserProfile profile;
    /* @Enumerated :
        JPA 엔티티 클래스에서 열거형(Enum) 타입의 필드를 매핑할 때 사용.
        이 어노테이션을 사용하면 해당 열거형 상수를 문자열로 데이터베이스에 저장하고 조회할 수 있다.
        두 가지 매핑 방식이 있다.
        1. ORDINAL : 열거형 상수가 선언된 순서에 따라 0, 1, 2, ...와 같은 숫자로 매핑
        2. STRING : 열거형 상수의 이름에 따라 데이터베이스에 저장
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus activityStatus;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    protected User() {
    }

    private User(String email, String password, UserStatus activityStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.email = email;
        this.password = password;
        this.activityStatus = activityStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User of(String email, String password) {
        return new User(
                email,
                password,
                UserStatus.INACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public void updateSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        this.updatedAt = LocalDateTime.now();
    }

    private void decryptEmail() {
        this.email = AESEncryption.decrypt(email);
    }

    public String getDecryptedEmail() {
        decryptEmail();
        return email;
    }
}
