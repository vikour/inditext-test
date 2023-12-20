package es.vikour92.inditext.test.domain.service;

import es.vikour92.inditext.test.domain.exceptions.DomainEntityNotFoundException;
import es.vikour92.inditext.test.domain.model.DateInterval;
import es.vikour92.inditext.test.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceService {

    /**
     *
     * @param brandId
     * @param productId
     * @param dateInterval
     *
     * @return
     *
     * @throws DomainEntityNotFoundException If Brand or Product could not be found by passed IDs
     */
    Optional<Price> find(long brandId, long productId, LocalDateTime dateTime) throws DomainEntityNotFoundException;

}
