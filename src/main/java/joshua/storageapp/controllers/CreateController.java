package joshua.storageapp.controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
        collection.setCreated(new Date());
        collection.setImageUrl("https://placehold.co/600x400");
        createDao.saveCollection(collection);
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
        container.setImageUrl("https://placehold.co/600x400");
        createDao.saveContainer(container);
        return "redirect:/collection/" + id;
    }

    @GetMapping("/create-item/{id}")
    public String showCreateItem(Model model, @PathVariable long id) {
        model.addAttribute("item", new Item());
        model.addAttribute("container", createDao.findContainerById(id));
        return "createItem";
    }

    @PostMapping("/create-item/{id}")
    public String createItem(@PathVariable long id, @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description, @RequestParam(name = "quantity") int quantity,
            @RequestParam(name = "tags") String tags) {
        Item item = new Item(new Date(), name, description, quantity);
        item.setContainer(createDao.findContainerById(id));
        String[] tagsArray = tags.split(" ");
        Set<Tag> tagSet = new HashSet<Tag>();
        for (String tagName : tagsArray) {
            if (createDao.checkIfTagExists(tagName)) {
                tagSet.add(createDao.findTagByName(tagName));
            } else {
                Tag newTag = new Tag(tagName);
                createDao.createTag(newTag);
                tagSet.add(newTag);
            }
        }
        item.setTags(tagSet);
        item.setImageUrl("https://placehold.co/600x400");
        createDao.saveItem(item);
        return "redirect:/container/" + id;
    }

}
