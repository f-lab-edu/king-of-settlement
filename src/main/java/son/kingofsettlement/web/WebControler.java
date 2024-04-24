package son.kingofsettlement.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import son.kingofsettlement.user.repository.UserRepository;

@Controller
@RequestMapping("/web")
@RequiredArgsConstructor
public class WebControler {

    private final UserRepository userRepository;

    @GetMapping("/signup")
    public String signup(Model model) {
        return "signup";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
