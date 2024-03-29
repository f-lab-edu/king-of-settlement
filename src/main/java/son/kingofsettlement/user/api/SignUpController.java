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
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignUpController {
	private final SignUpService signUpService;

	/*
		회원가입
	 */
	@PostMapping("/signup")
	public SignUpResponse post(@RequestBody SignUpRequest req) throws Exception {
		return signUpService.join(new User(req.email(), req.password(), req.nickname()));
	}
}
