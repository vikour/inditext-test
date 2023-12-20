package es.vikour92.inditext.test.domain.service;

import es.vikour92.inditext.test.domain.exceptions.DomainEntityNotFoundException;
import es.vikour92.inditext.test.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceService {

    /**
     * Finds the PVP of a brand and product in a specific date. The PVP is the highest product's price by priority in the
     * datetime passed as argument.
     *
     * @param brandId    A <code>Long</code> as brand identifier
     * @param productId  A <code>Long</code> as Product identifier
     * @param dateTime   A datetime in which product's price is available
     *
     * @return May the PVP product's price
     *
     * @throws DomainEntityNotFoundException If Brand or Product could not be found by passed IDs
     */
    Optional<Price> findPVP(long brandId, long productId, LocalDateTime dateTime) throws DomainEntityNotFoundException;

}
