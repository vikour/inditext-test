package es.vikour92.inditext.test.infrastructure.mappers;

import es.vikour92.inditext.test.domain.model.Brand;
import es.vikour92.inditext.test.infrastructure.entities.BrandEntity;
import org.mapstruct.Mapper;

@Mapper
public interface BrandMapper {

    BrandEntity toEntity(Brand brand);

}
