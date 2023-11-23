package joshua.storageapp.repositories;

import joshua.storageapp.models.Tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

    @Query(value = "SELECT * FROM tags c WHERE c.name LIKE %?1%", nativeQuery = true)
    List<Tag> findTagsBySearchTerm(String searchTerm);
}