package son.kingofsettlement.user.api;

import javax.security.auth.login.LoginException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import son.kingofsettlement.user.dto.LogInRequest;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.dto.SignUpResponse;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.service.UserService;

// 해당 클래스가 RESTful 웹 서비스의 컨트롤러임을 나타내는 어노테이션으로, HTTP 요청과 응답을 처리하는 컨트롤러로 사용
@RestController
// 요청 URL과 이를 처리하는 메소드를 매핑하기 위한 어노테이션으로, 컨트롤러 메소드가 처리할 URL 패턴을 지정
@RequestMapping("/users")
// Lombok 라이브러리에서 제공하는 어노테이션으로, final 필드가 있는 생성자를 생성해주는 역할
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	/*
		회원가입
	 */
	// HTTP POST 요청을 처리하는 메소드임을 나타내는 어노테이션으로, 해당 메소드가 POST 요청을 처리하는 컨트롤러 메소드임을 표시
	@PostMapping("")
	public ResponseEntity<Object> post(
		// 스프링 MVC 컨트롤러의 메소드 매개변수에 사용하여 클라이언트가 보낸 HTTP 요청의 body에 있는 데이터를 매개변수로 전달 받을 때 사용
		@RequestBody
		// 	자바 플랫폼의 표준 스펙 중 하나인 Bean Validation(JSR-380)을 사용하여 입력 데이터의 유효성을 검사할 때 사용.
		// 주로 @RequestBody로 전달된 객체의 유효성을 검사하는 데에 사용되며, 만약 객체에 유효성 검사를 위한 어노테이션이 포함되어 있다면,
		// 객체의 유효성을 검사하고 유효하지 않은 경우에는 예외를 발생.
		@Valid SignUpRequest req, BindingResult bindingResult) {
		User user;
		try {
			if (bindingResult.hasFieldErrors()) {
				throw new SignUpException(bindingResult.getFieldError().getDefaultMessage());
			}
			user = userService.signUp(req);
		} catch (SignUpException e) {
			return ResponseEntity.badRequest().body(new SignUpResponse(e.getMessage(), null));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpResponse("SignUp Succeed!", user.getId()));
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid LogInRequest req, BindingResult bindingResult,
		final HttpServletRequest request) {
		User loginUser;
		try {
			if (bindingResult.hasFieldErrors()) {
				throw new LoginException(bindingResult.getFieldError().getDefaultMessage());
			}
			loginUser = userService.login(request, req);
		} catch (LoginException e) {
			return ResponseEntity.badRequest().body(new SignUpResponse("Login Fail!", null));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SignUpResponse("Login Succeed!", loginUser.getId()));
	}

	@GetMapping("/logout")
	public ResponseEntity<Object> logout(final HttpServletRequest request, final HttpServletResponse response) {
		userService.logout(request);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SignUpResponse("Logout Succeed!", null));
	}
}
