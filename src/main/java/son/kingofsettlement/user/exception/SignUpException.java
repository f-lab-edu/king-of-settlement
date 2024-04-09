package son.kingofsettlement.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

// 예외가 발생했을 때 지정한 HTTP 응답 코드를 반환
@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class SignUpException extends RuntimeException {
	public SignUpException(String message) {
		super(message);
	}
}