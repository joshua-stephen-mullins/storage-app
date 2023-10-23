package joshua.storageapp.repositories;

import joshua.storageapp.models.Container;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContainerRepository extends JpaRepository<Container, Long>{

}