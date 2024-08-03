package son.kingofsettlement.common.statusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserStatusCode implements StatusCodeInterface {
	LOGIN_SUCCESS(HttpStatus.OK.value(), 1001, "Login successful."),
	USER_CREATED(HttpStatus.CREATED.value(), 1000, "User created."),
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 1400, "User not found."),
	DUPLICATED_USER(HttpStatus.BAD_REQUEST.value(), 1402, "This is a duplicate email address."),
	LOGIN_FAILED(HttpStatus.UNAUTHORIZED.value(), 1403, "Login failed.");

	private final Integer httpStatusCode;
	private final Integer statusCode;
	private final String description;
}
