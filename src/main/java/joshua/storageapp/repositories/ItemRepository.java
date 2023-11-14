package joshua.storageapp.repositories;

import joshua.storageapp.models.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findById(long id);

    @Query(value = "SELECT * FROM items c WHERE c.summary LIKE %?1%", nativeQuery = true)
    List<Item> find(String searchTerm);
}