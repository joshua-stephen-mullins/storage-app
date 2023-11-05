package joshua.storageapp.controllers;

import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import joshua.storageapp.models.*;
import joshua.storageapp.services.CreateDaoService;
import joshua.storageapp.services.UserDaoService;

@Controller
class UserController {
    private UserDaoService userDao;
    private CreateDaoService createDao;

    public UserController(UserDaoService userDao, CreateDaoService createDao) {
        this.userDao = userDao;
        this.createDao = createDao;
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
        user.setCreated(new Date());
        user.setIsAdmin(false);
        userDao.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String showUserPage(Model model) {
        model.addAttribute("collections", createDao
                .findCollectionsByUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return "user";
    }

    @GetMapping("/edit-user")
    public String showEditUserPage(Model model) {
        model.addAttribute("user", userDao.getUserByUsername(
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
        return "editUser";
    }

    @PostMapping("/edit-user")
    public String editUser(@RequestParam(name = "username") String username, @RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName, @RequestParam(name = "email") String email) {
        User user = userDao.getUserByUsername(
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userDao.saveUser(user);
        return "redirect:/user";
    }
}