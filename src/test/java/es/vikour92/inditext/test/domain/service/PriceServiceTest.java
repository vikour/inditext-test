package es.vikour92.inditext.test.domain.service;

import es.vikour92.inditext.test.domain.exceptions.DomainEntityNotFoundException;
import es.vikour92.inditext.test.domain.model.Brand;
import es.vikour92.inditext.test.domain.model.DateInterval;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.model.Product;
import es.vikour92.inditext.test.domain.ports.PricePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    private PriceService priceService;

    private PricePersistencePort pricePersistencePort;

    PriceService buildPriceService() {
        pricePersistencePort = mock(PricePersistencePort.class);
        return new PriceServiceImpl(pricePersistencePort);
    }

    @BeforeEach
    public void setupTest() {
        priceService = buildPriceService();
    }

    @Test
    public void testFind_whenBrandIdNotFound_thenError() {
        TestParam tp = TestParam.newDefault();
        DomainEntityNotFoundException expectedException = buildException(Brand.class, tp.brandId());

        // mocks
        when(pricePersistencePort.find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange())))
                .thenThrow(expectedException);

        // test
        DomainEntityNotFoundException exThrown = assertThrows(DomainEntityNotFoundException.class, () -> {
            priceService.findPVP(tp.brandId(), tp.productId(), tp.dateTimeInRange());
        });

        // checks
        assertEquals(expectedException, exThrown);
        verify(pricePersistencePort).find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange()));
    }

    @Test
    public void testFind_whenProductIdNotFound_thenError() {
        TestParam tp = TestParam.newDefault();
        DomainEntityNotFoundException expectedException = buildException(Product.class, tp.productId());

        // mocks
        when(pricePersistencePort.find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange())))
                .thenThrow(expectedException);

        // test
        DomainEntityNotFoundException exThrown = assertThrows(DomainEntityNotFoundException.class, () -> {
            priceService.findPVP(tp.brandId(), tp.productId(), tp.dateTimeInRange());
        });

        // checks
        assertEquals(expectedException, exThrown);
        verify(pricePersistencePort).find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange()));
    }

    @Test
    public void testFind_whenNoPrices_thenEmpty() {
        TestParam tp = TestParam.newDefault();
        List<Price> expectedPriceList = new ArrayList<>();

        // mocks
        when(pricePersistencePort.find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange())))
                .thenReturn(expectedPriceList);

        // test
        Optional<Price> mayAPrice = priceService.findPVP(tp.brandId(), tp.productId(), tp.dateTimeInRange());

        // checks
        assertTrue(mayAPrice.isEmpty());
        verify(pricePersistencePort).find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange()));
    }

    @Test
    public void testFind_whenJustOnePrice_thenOk() {
        TestParam tp = TestParam.newDefault();

        Price dbPrice = Price.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(10L))
                .priority(5L)
                .startDate(tp.dateInterval.start())
                .endDate(tp.dateInterval.end())
                .product(tp.product)
                .build();

        List<Price> pricesInBD = List.of(dbPrice);

        // mocks
        when(pricePersistencePort.find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange())))
                .thenReturn(pricesInBD);

        // test
        Optional<Price> mayAPrice = priceService.findPVP(tp.brandId(), tp.productId(), tp.dateTimeInRange());

        // checks
        assertTrue(mayAPrice.isPresent());
        Price priceReturned = mayAPrice.get();
        assertEquals(dbPrice, priceReturned);

        verify(pricePersistencePort).find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange()));
    }

    @Test
    public void testFind_whenMultiplePricesInInterval_thenCorrect() {
        TestParam tp = TestParam.newDefault();
        Price.Builder priceBuilder = Price.builder()
                .startDate(tp.dateInterval.start())
                .endDate(tp.dateInterval.end())
                .product(tp.product);

        Price priceA = priceBuilder
                .id(1L).priority(5L)
                .amount(BigDecimal.ONE)
                .build();
        Price priceB = priceBuilder
                .id(1L).priority(3L)
                .amount(BigDecimal.TEN)
                .build();
        Price priceC = priceBuilder
                .id(1L).priority(8L)
                .amount(BigDecimal.valueOf(1024))
                .build();
        List<Price> pricesInBD = List.of(priceA, priceB, priceC);

        // Mocks
        when(pricePersistencePort.find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange())))
                .thenReturn(pricesInBD);

        // test
        Optional<Price> mayATopPrice = priceService.findPVP(tp.brandId(), tp.productId(), tp.dateTimeInRange());

        // checks
        assertNotNull(mayATopPrice);
        assertTrue(mayATopPrice.isPresent());
        Price topPrice = mayATopPrice.get();
        assertEquals(priceC, topPrice);
    }


    @Test
    public void testFind_whenPriorityCollision_thenCorrect() {
        TestParam tp = TestParam.newDefault();
        Price.Builder priceBuilder = Price.builder()
                .startDate(tp.dateInterval.start())
                .endDate(tp.dateInterval.end())
                .product(tp.product);

        // Only A and B has the same priority, so some of them are price candidates.
        Price priceA = priceBuilder
                .id(1L).priority(5L)
                .amount(BigDecimal.ONE)
                .build();
        Price priceB = priceBuilder
                .id(1L).priority(5L)
                .amount(BigDecimal.TEN)
                .build();
        Price priceC = priceBuilder
                .id(1L).priority(2L)
                .amount(BigDecimal.valueOf(1024))
                .build();
        List<Price> pricesInBD = List.of(priceA, priceB, priceC);

        // Mocks
        when(pricePersistencePort.find(eq(tp.brandId()), eq(tp.productId()), eq(tp.dateTimeInRange())))
                .thenReturn(pricesInBD);

        // test
        Optional<Price> mayATopPrice = priceService.findPVP(tp.brandId(), tp.productId(), tp.dateTimeInRange());

        // checks
        assertNotNull(mayATopPrice);
        assertTrue(mayATopPrice.isPresent());
        Price topPrice = mayATopPrice.get();

        // It's not defined which price, A or B, must return the service. Either of them.
        assertTrue(Objects.equals(topPrice, priceA) || Objects.equals(topPrice, priceB));
    }

    private DomainEntityNotFoundException buildException(Class<?> entityClass, long id) {
        return new DomainEntityNotFoundException(entityClass, id);
    }

    private record TestParam (Product product, DateInterval dateInterval) {

        Brand brand() {
            return product.brand();
        }

        long brandId() {
            return product.brand().id();
        }

        long productId() {
            return product.id();
        }

        LocalDateTime dateTimeInRange() {
            return dateInterval.start().plusNanos(1);
        }

        static TestParam newDefault() {
            Brand brand = new Brand(1L, "brand-name");
            Product product = new Product(2L, "productName", brand);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime nextDay = now.plusDays(1);
            return new TestParam(product, DateInterval.of(now, nextDay));
        }
    }


}