package son.kingofsettlement.common.statusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// Lombok 라이브러리에서 제공하는 기능 중 하나로, 모든 필드를 매개변수로 받는 생성자를 자동으로 생성한다.
@AllArgsConstructor
// Lombok 라이브러리에서 제공하는 기능 중 하나로, 클래스의 필드에 대한 Getter 메소드를 자동으로 생성한다.
@Getter
public enum CommonStatusCode implements StatusCodeInterface {
	Succeed(HttpStatus.OK.value(), 0000, "request succeed"),
	// 5xx
	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), -1, "internal server error."),
	// 4xx
	COMMON_BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 9000, ""),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST.value(), 9020, "invalid input value."),
	HTTP_CLIENT_ERROR(HttpStatus.BAD_REQUEST.value(), 9030, "http client error."),
	INVALID_REQUEST_PARAM(HttpStatus.BAD_REQUEST.value(), 9040, "invalid request param."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), 9100, "unauthorized"),
	PAYMENT_REQUIRED(HttpStatus.PAYMENT_REQUIRED.value(), 9200, "payment required"),
	FORBIDDEN(HttpStatus.FORBIDDEN.value(), 9300, "forbidden"),
	NOT_FOUND_URL(HttpStatus.NOT_FOUND.value(), 9400, "not found url request"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), 9500, "method not allowed.");

	private final Integer httpStatusCode;
	private final Integer statusCode;
	private final String description;
}
