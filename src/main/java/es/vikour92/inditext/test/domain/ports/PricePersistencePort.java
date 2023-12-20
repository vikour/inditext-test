package es.vikour92.inditext.test.domain.ports;

import es.vikour92.inditext.test.domain.model.DateInterval;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.exceptions.DomainEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

public interface PricePersistencePort {

    /**
     * Fetch in the data source a bunch of prices of a Brand and Product in a specific date time.
     *
     * @param brandId    A <code>Long</code> as brand identifier
     * @param productId  A <code>Long</code> as product identifier
     * @param dateTime   A dateTime in which product price is queried
     *
     * @return A product's prices collection in the date interval
     *
     * @throws DomainEntityNotFoundException If brandId or ProductId could not be found.
     */
    Collection<Price> find(long brandId, long productId, LocalDateTime dateTime) throws DomainEntityNotFoundException;

}
