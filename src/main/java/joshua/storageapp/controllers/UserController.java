package joshua.storageapp.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import joshua.storageapp.models.*;
import joshua.storageapp.services.CreateDaoService;
import joshua.storageapp.services.UserDaoService;

@Controller
class UserController {
    private UserDaoService userDao;
    private CreateDaoService createDao;

    public UserController(UserDaoService userDao){
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(Model model) {
        model.addAttribute("user", new User());
        return "redirect:/user";
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

    @GetMapping("/user")
    public String showUserPage(Model model) {
        // model.addAttribute("collections", createDao.findCollectionsByUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return "user";
    }
}