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
public class CreateController {
    private CreateDaoService createDao;

    public CreateController(CreateDaoService createDao) {
        this.createDao = createDao;
    }

    @GetMapping("/create-collection")
    public String showCreateCollection(Model model) {
        model.addAttribute("collection", new Collection());
        return "createCollection";
    }

    @PostMapping("/create-collection")
    public String createCollection(@ModelAttribute Collection collection) {
        collection.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        createDao.createCollection(collection);
        return "redirect:/user";
    }

}
