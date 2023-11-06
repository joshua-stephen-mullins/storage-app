package joshua.storageapp.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import joshua.storageapp.services.CreateDaoService;
import joshua.storageapp.models.*;

@Controller
public class EditController {
    private CreateDaoService createDao;

    public EditController(CreateDaoService createDao) {
        this.createDao = createDao;
    }

    @GetMapping("/edit-collection/{id}")
    public String showEditCollection(Model model, @PathVariable long id) {
        if (((createDao.findCollectionById(id)).getUser()
                .getId()) != ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            return "redirect:/user";
        } else {
            model.addAttribute("collection", createDao.findCollectionById(id));
            return "editCollection";
        }
    }

    @PostMapping("/edit-collection/{id}")
    public String editCollection(@PathVariable long id, @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description) {
        Collection collection = createDao.findCollectionById(id);
        collection.setName(name);
        collection.setDescription(description);
        createDao.saveCollection(collection);
        return "redirect:/collection/" + collection.getId();
    }

    @GetMapping("/edit-container/{id}")
    public String showEditContainer(Model model, @PathVariable long id) {
        if (((createDao.findContainerById(id)).getCollection().getUser()
                .getId()) != ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            return "redirect:/user";
        } else {
            model.addAttribute("container", createDao.findContainerById(id));
            return "editContainer";
        }
    }

    @PostMapping("/edit-container/{id}")
    public String editContainer(@PathVariable long id, @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description) {
        Container container = createDao.findContainerById(id);
        container.setName(name);
        container.setDescription(description);
        createDao.saveContainer(container);
        return "redirect:/container/" + container.getId();
    }

    @GetMapping("/edit-item/{id}")
    public String showEditItem(Model model, @PathVariable long id) {
        if (((createDao.findItemById(id)).getContainer().getCollection().getUser()
                .getId()) != ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            return "redirect:/user";
        } else {
            Item item = createDao.findItemById(id);
            String tagsString = "";
            for (Tag tag : item.getTags()){
                tagsString += tag.getName() + " ";
            }
            model.addAttribute("item", item);
            model.addAttribute("tagsString", tagsString);
            System.out.println(tagsString);
            return "editItem";
        }
    }

    @PostMapping("/edit-item/{id}")
    public String editItem(@PathVariable long id, @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description, @RequestParam(name = "quantity") int quantity, @RequestParam(name = "tagsString") String tags) {
        Item item = createDao.findItemById(id);
        item.setName(name);
        item.setQuantity(quantity);
        item.setDescription(description);
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
        createDao.saveItem(item);
        return "redirect:/item/" + item.getId();
    }

}
