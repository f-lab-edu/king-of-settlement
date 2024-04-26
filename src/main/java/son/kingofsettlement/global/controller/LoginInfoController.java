package son.kingofsettlement.global.controller;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import son.kingofsettlement.global.service.LoginInfoService;
import son.kingofsettlement.user.dto.LogInRequest;
import son.kingofsettlement.user.entity.User;
import son.kingofsettlement.user.exception.UserDoseNotExist;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginInfoController {
	private final LoginInfoService loginInfoService;

	@Transactional(readOnly = true)
	@GetMapping("/user")
	public Optional<User> getLoginUser(HttpServletRequest request, LogInRequest req) throws UserDoseNotExist {
		return loginInfoService.getLoginUser(request);
	}
}

