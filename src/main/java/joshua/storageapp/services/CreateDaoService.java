package joshua.storageapp.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import joshua.storageapp.models.Collection;
import joshua.storageapp.models.User;
import joshua.storageapp.repositories.*;

@Service
public class CreateDaoService {
    private final UserDaoService userDao;
    private final CollectionRepository collectionDao;
    private final ContainerRepository containerDao;
    private final ItemRepository itemDao;

    public CreateDaoService(UserDaoService userDao, CollectionRepository collectionDao, ContainerRepository containerDao, ItemRepository itemDao){
        this.userDao = userDao;
        this.collectionDao = collectionDao;
        this.containerDao = containerDao;
        this.itemDao = itemDao;
    }

    public void createCollection(Collection collection){
        collection.setCreated(new Date());
        collectionDao.save(collection);
    }

    public List<Collection> findCollectionsByUser(User user){
        return collectionDao.findByUser(user);
    }
    
}