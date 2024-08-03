package son.kingofsettlement.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import son.kingofsettlement.common.CommonResponse;
import son.kingofsettlement.common.statusCode.CommonStatusCode;
import son.kingofsettlement.common.statusCode.UserStatusCode;
import son.kingofsettlement.user.dto.LogInRequest;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.exception.UserException;
import son.kingofsettlement.user.service.UserService;

// 해당 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타내는 어노테이션으로, HTTP 요청과 응답을 처리하는 컨트롤러로 사용
@RestController
// 요청 URL과 이를 처리하는 메소드를 매핑하기 위한 어노테이션으로, 컨트롤러 메소드가 처리할 URL 패턴을 지정
@RequestMapping("/users")
// Lombok 라이브러리에서 제공하는 어노테이션으로, final 필드가 있는 생성자를 생성해주는 역할
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	// HTTP POST 요청을 처리하는 메소드임을 나타내는 어노테이션으로, 해당 메소드가 POST 요청을 처리하는 컨트롤러 메소드임을 표시
	@PostMapping("")
	public ResponseEntity<CommonResponse<?>> signUp(
		// 스프링 MVC 컨트롤러의 메소드 매개변수에 사용하여 클라이언트가 보낸 HTTP 요청의 body에 있는 데이터를 매개변수로 전달 받을 때 사용
		@RequestBody
            /*
                자바 플랫폼의 표준 스펙 중 하나인 Bean Validation(JSR-380)을 사용하여 입력 데이터의 유효성을 검사할 때 사용.
                주로 @RequestBody로 전달된 객체의 유효성을 검사하는 데에 사용되며, 만약 객체에 유효성 검사를 위한 어노테이션이 포함되어 있다면,
                객체의 유효성을 검사하고 유효하지 않은 경우에는 예외를 발생.
             */ @Valid SignUpRequest req) {
		try {
			userService.signUp(req);
			return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(UserStatusCode.USER_CREATED));
		} catch (Exception e) {
			throw new UserException(CommonStatusCode.COMMON_BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<CommonResponse<?>> login(@RequestBody @Valid LogInRequest req,
		final HttpServletRequest request) {
		try {
			return ResponseEntity.ok()
				.body(CommonResponse.success(userService.login(request, req), UserStatusCode.LOGIN_SUCCESS));
		} catch (Exception e) {
			throw new UserException(UserStatusCode.LOGIN_FAILED, e.getMessage());
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<Object> logout(final HttpServletRequest request) {
		userService.logout(request);
		return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.success(CommonStatusCode.Succeed));
	}
}
