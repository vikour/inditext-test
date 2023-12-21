package es.vikour92.inditext.test.infrastructure.mappers;

import es.vikour92.inditext.test.domain.model.Product;
import es.vikour92.inditext.test.infrastructure.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper  {

    ProductEntity toEntity(Product product);

}
