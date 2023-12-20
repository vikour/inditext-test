package es.vikour92.inditext.test.domain.ports;

import es.vikour92.inditext.test.domain.model.DateInterval;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.exceptions.DomainEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PricePersistencePort {

    /**
     * Returns
     * @param brandId
     * @param productId
     * @param dateTime
     *
     * @return An iterable product's prices in the date interval
     *
     * @throws DomainEntityNotFoundException If brandId or ProductId could not be found.
     */
    Collection<Price> find(long brandId, long productId, LocalDateTime dateTime) throws DomainEntityNotFoundException;

}
