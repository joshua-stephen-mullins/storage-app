package joshua.storageapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import joshua.storageapp.models.*;
import joshua.storageapp.services.UserDaoService;

@Controller
class UserController {
    private UserDaoService userDao;

    public UserController(UserDaoService userDao){
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute User user) {
        userDao.registerUser(user);
        return "redirect:/login";
    }
}