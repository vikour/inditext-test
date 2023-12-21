package es.vikour92.inditext.test.infrastructure.repository;

import es.vikour92.inditext.test.infrastructure.entities.BrandEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends CrudRepository<BrandEntity,Long> {
}
