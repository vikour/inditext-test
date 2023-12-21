package es.vikour92.inditext.test.infrastructure.adapters;

import es.vikour92.inditext.test.domain.model.Brand;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.model.Product;
import es.vikour92.inditext.test.domain.ports.PricePersistencePort;
import es.vikour92.inditext.test.domain.ports.PricePersistencePortTest;
import es.vikour92.inditext.test.infrastructure.mappers.BrandMapper;
import es.vikour92.inditext.test.infrastructure.mappers.PriceEntityMapper;
import es.vikour92.inditext.test.infrastructure.mappers.ProductMapper;
import es.vikour92.inditext.test.infrastructure.repository.BrandRepository;
import es.vikour92.inditext.test.infrastructure.repository.PriceRepository;
import es.vikour92.inditext.test.infrastructure.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@SpringBootTest
@Transactional
class PricePersistenceAdapterIT extends PricePersistencePortTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PlatformTransactionManager txManager;

    @Autowired
    private PricePersistenceAdapter pricePersistenceAdapter;
    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    PriceEntityMapper priceMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    BrandMapper brandMapper;

    @Override
    protected PricePersistencePort createPersistencePort() {
        return pricePersistenceAdapter;
    }

    @Override
    protected void persist(Price... prices) {
        for(Price price : prices) {
            priceRepository.save(priceMapper.toEntity(price));
        }
    }

    @Override
    protected void persist(Product product) {
        productRepository.save(productMapper.toEntity(product));
    }

    @Override
    protected void persist(Brand brand) {
        brandRepository.save(brandMapper.toEntity(brand));
    }

    @Override
    protected void cleanPersistence() {
        TransactionTemplate tx = new TransactionTemplate(txManager);
        tx.execute(status -> {
            entityManager.createNativeQuery("DELETE FROM prices").executeUpdate();
            entityManager.createNativeQuery("DELETE FROM products").executeUpdate();
            entityManager.createNativeQuery("DELETE FROM brands").executeUpdate();
            entityManager.createNativeQuery("""
                    ALTER TABLE prices
                    ALTER COLUMN id
                    RESTART WITH 1
                    """).executeUpdate();
            entityManager.createNativeQuery("""
                    ALTER TABLE products
                    ALTER COLUMN id
                    RESTART WITH 1
                    """).executeUpdate();
            entityManager.createNativeQuery("""
                    ALTER TABLE brands
                    ALTER COLUMN id
                    RESTART WITH 1
                    """).executeUpdate();
            return 1;
        });
    }
}