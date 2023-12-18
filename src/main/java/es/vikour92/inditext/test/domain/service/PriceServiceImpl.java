package es.vikour92.inditext.test.domain.service;

import es.vikour92.inditext.test.domain.model.DateInterval;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.ports.PricePersistencePort;

import java.util.Optional;
import java.util.stream.StreamSupport;

public class PriceServiceImpl implements PriceService {

    private final PricePersistencePort pricePersistencePort;

    public PriceServiceImpl(PricePersistencePort pricePersistencePort) {
        this.pricePersistencePort = pricePersistencePort;
    }

    @Override
    public Optional<Price> find(long brandId, long productId, DateInterval dateInterval) {
        Iterable<Price> prices = pricePersistencePort.find(brandId, productId, dateInterval);
        return StreamSupport.stream(prices.spliterator(), false)
                .max(Price::compareByPriority);
    }
}
