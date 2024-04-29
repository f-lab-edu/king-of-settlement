package son.kingofsettlement.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/web")
public class WebController {

	@GetMapping("/signup")
	public String signup(Model model) {
		return "signup";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@GetMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return "home";
		}
		return "login";
	}

	@GetMapping("/profile")
	public String profile(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return "profile";
		}
		return "login";
	}
}
