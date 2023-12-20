package es.vikour92.inditext.test.domain.ports.stub;

import es.vikour92.inditext.test.domain.exceptions.DomainEntityNotFoundException;
import es.vikour92.inditext.test.domain.model.Brand;
import es.vikour92.inditext.test.domain.model.DateInterval;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.model.Product;
import es.vikour92.inditext.test.domain.ports.PricePersistencePort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Stub memory implementation of PricePersistenecePort for mocking and abstract test of
 * {@link es.vikour92.inditext.test.domain.ports.PricePersistencePortTest}
 */

public class MemoryPersistencePort implements PricePersistencePort {

    private final Set<Price> prices = new HashSet<>();
    private final Set<Product> products = new HashSet<>();
    private final Set<Brand> brands = new HashSet<>();

    @Override
    public Collection<Price> find(long brandId, long productId, LocalDateTime dateTime) throws DomainEntityNotFoundException {
        checkExistsBrand(brandId);
        checkExistsProduct(productId);
        return prices.stream()
                .filter(p -> p.getProduct().id() == productId &&
                        !dateTime.isBefore(p.getStartDate()) &&
                        !dateTime.isAfter(p.getEndDate()))
                .collect(Collectors.toList());
    }

    private void checkExistsProduct(long productId) {
        products.stream()
                .filter(product -> productId == product.id())
                .findAny()
                .orElseThrow(() -> new DomainEntityNotFoundException(Product.class, productId));
    }

    private void checkExistsBrand(long brandId) {
        brands.stream()
                .filter(brand -> brandId == brand.id())
                .findAny()
                .orElseThrow(() -> new DomainEntityNotFoundException(Brand.class, brandId));
    }

    public void cleanUp() {
        prices.clear();
        brands.clear();
        products.clear();
    }

    public void persist(Price ... prices) {
        this.prices.addAll(Arrays.stream(prices).toList());
    }

    public void persist(Brand brand) {
        this.brands.add(brand);
    }

    public void persist(Product product) {
        this.products.add(product);
    }
}
