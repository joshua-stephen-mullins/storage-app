package joshua.storageapp.controllers;

import java.util.Date;

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

    @GetMapping("/create-container/{id}")
    public String showCreateContainer(Model model, @PathVariable long id) {
        model.addAttribute("container", new Container());
        model.addAttribute("collection", createDao.findCollectionById(id));
        return "createContainer";
    }

    @PostMapping("/create-container/{id}")
    public String createContainer(@PathVariable long id, @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description) {
        long collectionId = id;
        Container container = new Container(createDao.findCollectionById(collectionId), new Date(), name, description);
        createDao.createContainer(container);
        return "redirect:/collection/" + id;
    }

    @GetMapping("/create-item/{id}")
    public String showCreateItem(Model model, @PathVariable long id) {
        model.addAttribute("item", new Item());
        model.addAttribute("container", createDao.findContainerById(id));
        return "createItem";
    }

    @PostMapping("/create-item/{id}")
    public String createItem(@PathVariable long id, @RequestParam(name = "name") String name, @RequestParam(name = "description") String description, @RequestParam(name = "quantity") int quantity){
        Item item = new Item(new Date(), name, description, quantity);
        item.setContainer(createDao.findContainerById(id));
        createDao.createItem(item);
        return "redirect:/container/" + id;
    }

}
