package joshua.storageapp.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import joshua.storageapp.models.*;
import joshua.storageapp.services.CreateDaoService;

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

    @GetMapping("/create-container")
    public String showCreateContainer(Model model) {
        model.addAttribute("container", new Container());
        return "createContainer";
    }

    @PostMapping("/create-container")
    public String createContainer(@ModelAttribute Container container, @RequestParam(name = "collectionId") int collectionId) { 
        container.setCollection(createDao.findCollectionById(collectionId));
        createDao.createContainer(container);
        return "redirect:/user";
    }

}
