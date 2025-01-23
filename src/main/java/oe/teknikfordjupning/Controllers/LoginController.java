package oe.teknikfordjupning.Controllers;


import lombok.RequiredArgsConstructor;
import oe.teknikfordjupning.Models.User;
import oe.teknikfordjupning.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        try {
            User currentUser = userService.getCurrentUser();
            model.addAttribute("user", currentUser);
        } catch (IllegalStateException e) {
            model.addAttribute("user", null);
        }
        return "index";
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping(path = "/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        model.addAttribute("activeFunction", "login");

        User user = userService.findByEmail(email).get();
        if (user != null) {
            model.addAttribute("userEmail", user.getEmail());
        }

        return "login";
    }

}
