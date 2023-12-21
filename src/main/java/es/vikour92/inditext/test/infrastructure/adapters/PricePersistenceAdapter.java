package es.vikour92.inditext.test.infrastructure.adapters;

import es.vikour92.inditext.test.domain.exceptions.DomainEntityNotFoundException;
import es.vikour92.inditext.test.domain.model.Brand;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.model.Product;
import es.vikour92.inditext.test.domain.ports.PricePersistencePort;
import es.vikour92.inditext.test.infrastructure.entities.PriceEntity;
import es.vikour92.inditext.test.infrastructure.entities.ProductEntity;
import es.vikour92.inditext.test.infrastructure.mappers.PriceEntityMapper;
import es.vikour92.inditext.test.infrastructure.repository.BrandRepository;
import es.vikour92.inditext.test.infrastructure.repository.PriceRepository;
import es.vikour92.inditext.test.infrastructure.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
@AllArgsConstructor
public class PricePersistenceAdapter implements PricePersistencePort {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final PriceEntityMapper priceEntityMapper;

    @Override
    public Collection<Price> find(long brandId, long productId, LocalDateTime dateTime) throws DomainEntityNotFoundException {
        brandRepository.findById(brandId).orElseThrow(() -> new DomainEntityNotFoundException(Brand.class, brandId));
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new DomainEntityNotFoundException(Product.class, productId));
        Collection<PriceEntity> priceEntities = priceRepository.findAllInRange(product, dateTime);
        return priceEntityMapper.toDomain(priceEntities);
    }
}
