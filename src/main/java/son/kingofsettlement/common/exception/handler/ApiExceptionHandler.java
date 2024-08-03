package son.kingofsettlement.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import son.kingofsettlement.common.CommonResponse;
import son.kingofsettlement.common.statusCode.StatusCodeInterface;
import son.kingofsettlement.user.exception.UserException;

@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {

	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<CommonResponse<Object>> userException(UserException userException) {
		log.error(">>>>>>> UserException", userException);
		StatusCodeInterface errorCode = userException.getStatusCodeInterface();

		return ResponseEntity.status(errorCode.getHttpStatusCode()).body(CommonResponse.fail(errorCode, userException.getErrorDescription()));
	}
}
