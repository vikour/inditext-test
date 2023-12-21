package es.vikour92.inditext.test.infrastructure.mappers;

import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.infrastructure.entities.PriceEntity;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper
public interface PriceEntityMapper {

    Price toDomain(PriceEntity price);

    Collection<Price> toDomain(Collection<PriceEntity> priceEntities);

    PriceEntity toEntity(Price price);
}
