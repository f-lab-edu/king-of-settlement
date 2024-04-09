package son.kingofsettlement.user.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.exception.SignUpException;
import son.kingofsettlement.user.service.SignUpService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final SignUpService signUpService;

	/*
		회원가입
	 */
	@PostMapping("")
	public ResponseEntity<String> post(@RequestBody SignUpRequest req) {
		try {
			signUpService.signUp(req);
		} catch (SignUpException e) {
			return ResponseEntity.badRequest().body("회원가입 실패 : " + e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!");
	}
}
