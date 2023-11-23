package joshua.storageapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import joshua.storageapp.models.*;
import joshua.storageapp.repositories.*;

@Service
public class CreateDaoService {

    private final UserDaoService userDao;
    private final CollectionRepository collectionDao;
    private final ContainerRepository containerDao;
    private final ItemRepository itemDao;
    private final TagRepository tagDao;

    public CreateDaoService(UserDaoService userDao, CollectionRepository collectionDao,
            ContainerRepository containerDao, ItemRepository itemDao, TagRepository tagDao) {
        this.userDao = userDao;
        this.collectionDao = collectionDao;
        this.containerDao = containerDao;
        this.itemDao = itemDao;
        this.tagDao = tagDao;
    }

    public void saveCollection(Collection collection) {
        collectionDao.save(collection);
    }

    public void saveContainer(Container container) {
        containerDao.save(container);
    }

    public void saveItem(Item item) {
        itemDao.save(item);
    }

    public void createTag(Tag tag) {
        tagDao.save(tag);
    }

    public List<Collection> findCollectionsByUser(User user) {
        return collectionDao.findByUser(user);
    }

    public Collection findCollectionById(long id) {
        return collectionDao.findById(id);
    }

    public Container findContainerById(long id) {
        return containerDao.findById(id);
    }

    public Item findItemById(long id) {
        return itemDao.findById(id);
    }

    public Tag findTagByName(String name) {
        return tagDao.findByName(name);
    }

    public boolean checkIfTagExists(String name) {
        if (tagDao.findByName(name) != null) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Item> getItemsBySearchTerm(String searchTerm, long collectionId) {

        List<Item> items = itemDao.findItemsBySearchTerm(searchTerm);
        ArrayList<Item> filteredItems = new ArrayList<>();
        List<Tag> tags = tagDao.findTagsBySearchTerm(searchTerm);
        for (Tag tag : tags) {
            for (Item item : tag.getItems()) {
                boolean contains = false;
                for (int i = 0; i < items.size(); i++) {
                    if (item.getId() == items.get(i).getId()) {
                        contains = true;
                    }
                }
                if (!contains) {
                    items.add(item);
                }
            }
        }
        for (Item item : items) {
            if (item.getContainer().getCollection().getId() == collectionId) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

}
