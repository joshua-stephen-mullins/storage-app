package joshua.storageapp.repositories;

import joshua.storageapp.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TagRepository extends JpaRepository<Tag, Long>{

    Tag findByName(String name);

}