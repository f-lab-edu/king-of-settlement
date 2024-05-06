package son.kingofsettlement.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import son.kingofsettlement.common.CommonResponse;
import son.kingofsettlement.common.exception.ApiException;
import son.kingofsettlement.common.exception.UserException;
import son.kingofsettlement.common.statusCode.StatusCodeInterface;

/*
	@Slf4j:
	어노테이션은 Lombok 라이브러리에서 제공하는 기능 중 하나로,
	어노테이션을 클래스에 적용하면 자동으로 해당 클래스의 Logger 인스턴스를 생성하고, 이를 사용하여 로그를 출력할 수 있다.
*/
@Slf4j
/*
	@RestControllerAdvice:
		일반적으로 RESTful 웹 서비스에서 예외 처리를 담당하는 클래스에 사용된다. 이 어노테이션을 클래스에 붙이면 해당 클래스는 전역 예외 처리를 담당하게 된다.
	@ControllerAdvice:
		@Controller와 같이 @ResponseBody 어노테이션이 제외된것으로, 전통적인 MVC 웹 애플리케이션에서 사용된다.
*/
@RestControllerAdvice
// @Order: 스프링 애플리케이션에서 여러 개의 빈이 동일한 타입을 가질 때, 빈들간 우선 순위를 지정하는 데 사용된다.
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {
	/*
		@ExceptionHandler:
		@RestControllerAdvice 나, @ControllerAdvice 와 같이 전역적인 예외처리를 담당하는 클래스내에 정의되며,
		특정 예외에 대한 처리를 지정하는 데 사용된다.
	*/
	@ExceptionHandler(value = ApiException.class)
	public ResponseEntity<CommonResponse<Object>> apiException(ApiException apiException) {
		log.error(">>>>>> ApiException", apiException);
		StatusCodeInterface errorCode = apiException.getStatusCodeInterface();

		return ResponseEntity.status(errorCode.getHttpStatusCode()).body(CommonResponse.fail(errorCode, errorCode.getDescription()));
	}

	/* @ExceptionHandler:
		@RestControllerAdvice 나, @ControllerAdvice 와 같이 전역적인 예외처리를 담당하는 클래스내에 정의되며,
		특정 예외에 대한 처리를 지정하는 데 사용된다.
	*/
	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<CommonResponse<Object>> userException(UserException userException) {
		log.error(">>>>>>> UserException", userException);
		StatusCodeInterface errorCode = userException.getStatusCodeInterface();

		return ResponseEntity.status(errorCode.getHttpStatusCode()).body(CommonResponse.fail(errorCode, userException.getErrorDescription()));
	}
}
