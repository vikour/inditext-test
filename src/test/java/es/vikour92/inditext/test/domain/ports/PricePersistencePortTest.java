package es.vikour92.inditext.test.domain.ports;

import es.vikour92.inditext.test.domain.exceptions.DomainEntityNotFoundException;
import es.vikour92.inditext.test.domain.model.Brand;
import es.vikour92.inditext.test.domain.model.DateInterval;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.model.Product;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains all test cases required for those PricePersistencePort's implementations
 */
public abstract class PricePersistencePortTest {

    protected PricePersistencePort persistencePort;

    /**
     * PricePersistencePort's method implementations
     *
     * @return An implementation of PricePersistencePort
     */

    protected abstract PricePersistencePort createPersistencePort();

    /**
     * Stores a prices in data source
     *
     * @param prices One or multiple Prices
     */

    protected abstract void persist(Price... prices);

    /**
     * Stores a product in data source
     *
     * @param product A Product
     */

    protected abstract void persist(Product product);

    /**
     * Stores brand in data source
     *
     * @param brand A Brand
     */

    protected abstract void persist(Brand brand);

    /**
     * Cleans all prices, products and brands in data source
     */

    protected abstract void cleanPersistence();

    @BeforeEach
    public void cleanUpTest() {
        persistencePort = createPersistencePort();
        cleanPersistence();
    }

    @Test
    @DisplayName("Should no return anything if brand does not exists")
    public void testFind_whenNoExistsBrand_thenError() {
        long brandId = 1L;
        long productId = 2L;
        LocalDateTime dateTime = LocalDateTime.of(2000, 6, 10, 10, 0);

        DomainEntityNotFoundException ex = assertThrows(DomainEntityNotFoundException.class, () -> {
            persistencePort.find(brandId, productId, dateTime);
        }, "Should throw an exception if brand was not found");

        assertEquals(Brand.class, ex.getDomainClass());
        assertEquals(brandId, ex.getDomainId());
    }

    @Test
    @DisplayName("Should no return anything if product does not exists")
    public void testFind_whenNoExistsProduct_thenError() {
        Brand brand = new Brand(1L, "::brand::");
        long productId = 2L;
        LocalDateTime dateTime = LocalDateTime.of(2000, 6, 10, 10, 0);

        persist(brand);

        DomainEntityNotFoundException ex = assertThrows(DomainEntityNotFoundException.class, () -> {
            persistencePort.find(brand.id(), productId, dateTime);
        }, "Should throw an exception if product was not found");

        assertEquals(Product.class, ex.getDomainClass());
        assertEquals(productId, ex.getDomainId());
    }

    @Test
    @DisplayName("Should no return anything if there is no prices in DB")
    public void test_whenNoPrices_thenEmpty() {
        Brand brand = new Brand(1L, "::brand::");
        Product product = new Product(1L, "::product::", brand);
        LocalDateTime dateTime = LocalDateTime.of(2000, 6, 10, 10, 0);

        persist(brand);
        persist(product);

        Iterable<Price> prices = persistencePort.find(brand.id(), product.id(), dateTime);

        long size = prices.spliterator().estimateSize();
        assertEquals(0, size);
    }

    @Nested
    @DisplayName("With one prices in DB")
    public class TestCaseOnePrice {
        private final DateInterval PRICE_INTERVAL = DateInterval.of(
                LocalDateTime.of(2000, 6, 10, 10, 0),
                LocalDateTime.of(2000, 6, 10, 10, 30)
        );
        private Price priceStored;

        @BeforeEach
        public void setUp() {
            Brand brand = new Brand(1L, "brand");
            Product product = new Product(1L, "product", brand);
            priceStored = Price.builder()
                    .id(1L)
                    .startDate(PRICE_INTERVAL.start())
                    .endDate(PRICE_INTERVAL.end())
                    .priority(2L)
                    .amount(BigDecimal.valueOf(42L))
                    .product(product)
                    .build();
            persist(brand);
            persist(product);
            persist(priceStored);
        }

        public long priceBrandId() {
            return priceStored.getProduct().brand().id();
        }

        public long priceProductId() {
            return priceStored.getProduct().id();
        }

        @Test
        @DisplayName("Query date in range")
        public void test_whenIntervalAreSame_thenCorrect() {
            Collection<Price> prices = persistencePort.find(priceBrandId(), priceProductId(), PRICE_INTERVAL.start());
            assertTrue(prices.contains(priceStored));
        }

        @Test
        @DisplayName("Query date out of range")
        public void test_whenIntervalTopOut_thenNothingReturned() {
            Collection<Price> prices = persistencePort.find(priceBrandId(), priceProductId(), PRICE_INTERVAL.end().plusNanos(1));
            assertTrue(prices.isEmpty());
        }


    }

    @Nested
    @DisplayName("With multiple prices")
    public class TestCaseThreePrices {
        private Price priceA;
        private Price priceB;
        private Price priceC;

        private static final LocalDateTime ONE_JUNE = LocalDateTime.of(2000, 6, 1, 10, 10);
        private static final LocalDateTime TWO_JUNE = LocalDateTime.of(2000, 6, 2, 10, 10);
        private static final LocalDateTime THREE_JUNE = LocalDateTime.of(2000, 6, 3, 10, 10);

        private Price buildPrice(long id, LocalDateTime start, LocalDateTime end, Product product) {
            return Price.builder()
                    .id(id)
                    .startDate(start)
                    .endDate(end)
                    .priority(2L)
                    .amount(BigDecimal.valueOf(42L))
                    .product(product)
                    .build();
        }

        @BeforeEach
        public void setUp() {
            Brand brand = new Brand(1L, "brand");
            Product product = new Product(1L, "product", brand);

            priceA = buildPrice(1L, ONE_JUNE, TWO_JUNE, product);
            priceB = buildPrice(2L, TWO_JUNE, THREE_JUNE, product);
            priceC = buildPrice(3L, ONE_JUNE, THREE_JUNE, product);

            persist(brand);
            persist(product);
            persist(priceA, priceB, priceC);
        }

        public long priceBrandId() {
            return priceA.getProduct().brand().id();
        }

        public long priceProductId() {
            return priceA.getProduct().id();
        }

        @Test
        @DisplayName("Request interval includes A and B")
        public void test_pricesIncludedAB() {
            Collection<Price> prices = persistencePort.find(priceBrandId(), priceProductId(), ONE_JUNE);
            assertEquals(2, prices.size());
            assertTrue(prices.contains(priceA));
            assertTrue(prices.contains(priceC));
        }

        @Test
        @DisplayName("Request interval includes B and C")
        public void testB_pricesIncludedBC() {
            Collection<Price> prices = persistencePort.find(priceBrandId(), priceProductId(), TWO_JUNE.plusNanos(1));
            assertEquals(2, prices.size());
            assertTrue(prices.contains(priceB));
            assertTrue(prices.contains(priceC));
        }

        @Test
        @DisplayName("Request interval includes A, B and C")
        public void test_pricesIncludedABC() {
            Collection<Price> prices = persistencePort.find(priceBrandId(), priceProductId(), TWO_JUNE);
            assertEquals(3, prices.size());
            assertTrue(prices.containsAll(List.of(priceA, priceB, priceC)));
        }

        @Test
        @DisplayName("Request interval does not include any prices")
        public void test_nonPricesIncluded() {
            Collection<Price> prices = persistencePort.find(priceBrandId(), priceProductId(),
                    THREE_JUNE.plusSeconds(1)
            );
            assertTrue(prices.isEmpty());
        }

    }



}