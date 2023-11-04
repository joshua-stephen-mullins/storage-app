package joshua.storageapp.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import joshua.storageapp.services.CreateDaoService;
import joshua.storageapp.models.*;

@Controller
public class EditController {
    private CreateDaoService createDao;

    public EditController(CreateDaoService createDao) {
        this.createDao = createDao;
    }

    @GetMapping("/edit-collection/{id}")
    public String showCreateCollection(Model model, @PathVariable long id) {
        if((createDao.findCollectionById(id)).getId() != ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()){
            return "redirect:/user";
        } else {
            model.addAttribute("collection", createDao.findCollectionById(id));
            return "editCollection";
        }
    }

}
