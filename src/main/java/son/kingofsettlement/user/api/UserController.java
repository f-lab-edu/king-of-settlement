package son.kingofsettlement.user.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import son.kingofsettlement.user.dto.SignUpRequest;
import son.kingofsettlement.user.dto.SignUpResponse;
import son.kingofsettlement.user.entity.User;
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
	public SignUpResponse post(@RequestBody SignUpRequest req) throws Exception {
		return signUpService.signUp(new User(req.email(), req.password(), req.nickname()));
	}
}
