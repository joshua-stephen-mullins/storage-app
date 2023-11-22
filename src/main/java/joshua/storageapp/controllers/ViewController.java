package joshua.storageapp.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import joshua.storageapp.models.Item;
import joshua.storageapp.models.SortByName;
import joshua.storageapp.services.CreateDaoService;

@Controller
public class ViewController {

    private CreateDaoService createDao;

    public ViewController(CreateDaoService createDao) {
        this.createDao = createDao;
    }

    @GetMapping("/collection/{id}/{sortType}")
    public String viewCollectionSorted(@PathVariable long id, @PathVariable String sortType, Model model) {
        model.addAttribute("collection", createDao.findCollectionById(id));

        if (sortType.equals("name-asc")) {
            List<Item> nameSorted = (createDao.findCollectionById(id).getItems());
            Collections.sort(nameSorted, new SortByName());
            model.addAttribute("items", nameSorted);
        } else if (sortType.equals("name-des")) {
            List<Item> nameSorted = (createDao.findCollectionById(id).getItems());
            Collections.sort(nameSorted, new SortByName());
            Collections.reverse(nameSorted);
            model.addAttribute("items", nameSorted);
        } else if (sortType.equals("date")) {
            model.addAttribute("items", createDao.findCollectionById(id).getItems());
        } else if (sortType.equals("quantity-asc")) {
            model.addAttribute("items", quantitySort(createDao.findCollectionById(id).getItems()));
        } else if (sortType.equals("quantity-des")) {
            List<Item> descSorted = quantitySort(createDao.findCollectionById(id).getItems());
            Collections.reverse(descSorted);
            model.addAttribute("items", descSorted);
        } else {
            model.addAttribute("items", createDao.findCollectionById(id).getItems());
        }
        return "/collection";
    }

    @GetMapping("/collection/{id}/search={searchTerm}")
    public String viewCollectionWithSearchTerm(@PathVariable long id, @PathVariable String searchTerm, Model model) {
        model.addAttribute("collection", createDao.findCollectionById(id));
        List<Item> items = createDao.getItemsBySearchTerm(searchTerm);
        ArrayList<Item> filteredItems = new ArrayList<>();
        for (Item item : items){
            if (item.getContainer().getCollection().getId() == id){
                filteredItems.add(item);
            }
        }
        model.addAttribute("items", filteredItems);
        return "/collection";
    }

    @GetMapping("/collection/{id}")
    public String viewCollection(@PathVariable long id, Model model) {
        model.addAttribute("collection", createDao.findCollectionById(id));
        model.addAttribute("items", createDao.findCollectionById(id).getItems());
        List<Item> test = createDao.getItemsBySearchTerm("dragon");
        for (Item item : test){
            System.out.println(">" + item.getName() + "<");
        }
        return "/collection";
    }

    @GetMapping("/container/{id}")
    public String viewContainer(@PathVariable long id, Model model) {
        model.addAttribute("container", createDao.findContainerById(id));
        return "/container";
    }

    @GetMapping("/item/{id}")
    public String viewItem(@PathVariable long id, Model model) {
        model.addAttribute("item", createDao.findItemById(id));
        return "/item";
    }

    // This method takes in an arraylist of songs as its argument and then sorts the
    // list by the songs popularity.
    public static List<Item> quantitySort(List<Item> sl) {
        List<Item> listCopy = new ArrayList<>(sl);
        // Declares variables for the startScan, index, minIndex and minValue.
        int startScan, index, minIndex;
        Item minValue;
        // Loop that runs once for each item in the arraylist.
        for (startScan = 0; startScan < (listCopy.size() - 1); startScan++) {
            // Sets minIndex equal to the counter variable for the loop.
            minIndex = startScan;
            // Sets the minValue as to the song object at the index of the startScan.
            minValue = listCopy.get(startScan);
            // Loop that starts at startScan + 1, and compares each songs popularity score
            // to the score of the song stored in minValue.
            for (index = startScan + 1; index < listCopy.size(); index++) {

                // If the song at the index of the nested loops popularity is less than the song
                // stored in minValue, minValue and minIndex are reassigned to the current
                // index.
                if (listCopy.get(index).getQuantity() < minValue.getQuantity()) {
                    minValue = listCopy.get(index);
                    minIndex = index;
                }
            }
            // Reassigns the order of the original arraylist according the minIndex and
            // startScan variables.
            listCopy.set(minIndex, listCopy.get(startScan));
            listCopy.set(startScan, minValue);
        }
        return listCopy;
    }

}
