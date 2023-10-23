package joshua.storageapp.repositories;

import joshua.storageapp.models.Collection;
import joshua.storageapp.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface CollectionRepository extends JpaRepository<Collection, Long>{

    List<Collection> findByUser(User user);

}