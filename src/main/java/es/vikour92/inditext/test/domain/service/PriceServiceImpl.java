package es.vikour92.inditext.test.domain.service;

import es.vikour92.inditext.test.domain.model.DateInterval;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.ports.PricePersistencePort;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class PriceServiceImpl implements PriceService {

    private final PricePersistencePort pricePersistencePort;

    public PriceServiceImpl(PricePersistencePort pricePersistencePort) {
        this.pricePersistencePort = pricePersistencePort;
    }

    @Override
    public Optional<Price> findPVP(long brandId, long productId, LocalDateTime dateTime) {
        Iterable<Price> prices = pricePersistencePort.find(brandId, productId, dateTime);
        return StreamSupport.stream(prices.spliterator(), false)
                .max(Price::compareByPriority);
    }
}
