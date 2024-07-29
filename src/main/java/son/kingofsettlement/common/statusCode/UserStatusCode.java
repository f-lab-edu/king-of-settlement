package son.kingofsettlement.common.statusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// Lombok 라이브러리에서 제공하는 기능 중 하나로, 모든 필드를 매개변수로 받는 생성자를 자동으로 생성한다.
@AllArgsConstructor
// Lombok 라이브러리에서 제공하는 기능 중 하나로, 클래스의 필드에 대한 Getter 메소드를 자동으로 생성한다.
@Getter
public enum UserStatusCode implements StatusCodeInterface {
	LOGIN_SUCCESS(HttpStatus.OK.value(), 1001, "로그인 인증이 성공하였습니다."),
	USER_CREATED(HttpStatus.CREATED.value(), 1000, "유저가 생성되었습니다."),
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 1400, "사용자를 찾을 수 없습니다."),
	DUPLICATED_USER(HttpStatus.BAD_REQUEST.value(), 1402, "중복된 이메일주소 입니다."),
	LOGIN_FAILED(HttpStatus.UNAUTHORIZED.value(), 1403, "로그인 인증이 실패하였습니다.");

	private final Integer httpStatusCode;
	private final Integer statusCode;
	private final String description;
}
