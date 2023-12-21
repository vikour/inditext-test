package es.vikour92.inditext.test.infrastructure.repository;

import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.infrastructure.entities.PriceEntity;
import es.vikour92.inditext.test.infrastructure.entities.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface PriceRepository extends CrudRepository<PriceEntity, Long> {

    @Query("""
       select p from Price p
       where p.product = :product
          and p.startDate <= :dateTime
          and p.endDate >= :dateTime
       """)
    Collection<PriceEntity> findAllInRange(ProductEntity product, LocalDateTime dateTime);

}
