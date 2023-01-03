package eu.deyanix.mobileoperator.controller;

import eu.deyanix.mobileoperator.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    private UserRepository userRepository;

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll(Pageable.ofSize(10)));
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
