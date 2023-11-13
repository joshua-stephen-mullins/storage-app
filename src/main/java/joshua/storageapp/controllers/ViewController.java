package joshua.storageapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import joshua.storageapp.services.CreateDaoService;

@Controller
public class ViewController {

    private CreateDaoService createDao;

    public ViewController(CreateDaoService createDao) {
        this.createDao = createDao;
    }


    @GetMapping("/collection/{id}")
    public String viewCollection(@PathVariable long id, Model model){
        model.addAttribute("collection", createDao.findCollectionById(id));
        model.addAttribute("items", createDao.findCollectionById(id).getItems());
        return "/collection";
    }

    @GetMapping("/container/{id}")
    public String viewContainer(@PathVariable long id, Model model){
        model.addAttribute("container", createDao.findContainerById(id));
        return "/container";
    }

    @GetMapping("/item/{id}")
    public String viewItem(@PathVariable long id, Model model){
        model.addAttribute("item", createDao.findItemById(id));
        return "/item";
    }
}
