package es.vikour92.inditext.test.infrastructure.repository;

import es.vikour92.inditext.test.infrastructure.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
}
