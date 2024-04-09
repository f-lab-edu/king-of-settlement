package son.kingofsettlement.user.api;

import static son.kingofsettlement.user.exception.UserValidator.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.dto.SignUpResponse;
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
	public ResponseEntity<SignUpResponse> post(@RequestBody @Valid SignUpRequest req, BindingResult bindingResult) {
		try {
			if (bindingResult.hasErrors()) {
				throw new SignUpException(bindingResult.getFieldError().getDefaultMessage());
			}
			validatePassword(req.getPassword());
			validateNickname(req.getNickname());
			signUpService.signUp(req);
		} catch (ValidationException | SignUpException e) {
			return ResponseEntity.badRequest().body(new SignUpResponse(e.getMessage()));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpResponse("SignUp Succeed"));
	}
}
