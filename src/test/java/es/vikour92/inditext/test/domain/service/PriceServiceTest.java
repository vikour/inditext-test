package es.vikour92.inditext.test.domain.service;

import es.vikour92.inditext.test.domain.exceptions.DomainEntityNotFoundException;
import es.vikour92.inditext.test.domain.model.Brand;
import es.vikour92.inditext.test.domain.model.DateInterval;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.model.Product;
import es.vikour92.inditext.test.domain.ports.PricePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        long brandId = 1L;
        long productId = 1L;
        DomainEntityNotFoundException expectedException = buildException(Brand.class, brandId);

        // mocks
        when(pricePersistencePort.find(eq(brandId), eq(productId), any()))
                .thenThrow(expectedException);

        // test
        DomainEntityNotFoundException exThrown = assertThrows(DomainEntityNotFoundException.class, () -> {
            priceService.find(brandId, productId, DateInterval.of(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        });

        // checks
        assertEquals(expectedException, exThrown);
        verify(pricePersistencePort).find(eq(brandId), eq(productId), any());
    }

    @Test
    public void testFind_whenProductIdNotFound_thenError() {
        long brandId = 1L;
        long productId = 1L;
        DomainEntityNotFoundException expectedException = buildException(Product.class, productId);

        // mocks
        when(pricePersistencePort.find(eq(brandId), eq(productId), any()))
                .thenThrow(expectedException);

        // test
        DomainEntityNotFoundException exThrown = assertThrows(DomainEntityNotFoundException.class, () -> {
            priceService.find(brandId, productId, DateInterval.of(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        });

        // checks
        assertEquals(expectedException, exThrown);
        verify(pricePersistencePort).find(eq(brandId), eq(productId), any());
    }

    @Test
    public void testFind_whenNoPrices_thenEmpty() {
        long brandId = 1L;
        long productId = 1L;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(1);
        DateInterval dateInterval = new DateInterval(startDate, endDate);

        List<Price> priceList = new ArrayList<>();

        // mocks
        when(pricePersistencePort.find(eq(brandId), eq(productId), eq(dateInterval)))
                .thenReturn(priceList);

        // test
        Optional<Price> mayAPrice = priceService.find(brandId, productId, dateInterval);

        // checks
        assertTrue(mayAPrice.isEmpty());
        verify(pricePersistencePort).find(eq(brandId), eq(productId), eq(dateInterval));
    }


    private DomainEntityNotFoundException buildException(Class<?> entityClass, long id) {
        return new DomainEntityNotFoundException(entityClass, id);
    }


}