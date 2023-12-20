package es.vikour92.inditext.test.domain.ports;

import es.vikour92.inditext.test.domain.model.Brand;
import es.vikour92.inditext.test.domain.model.Price;
import es.vikour92.inditext.test.domain.model.Product;
import es.vikour92.inditext.test.domain.ports.stub.MemoryPersistencePort;

class MemoryPersistencePortTest extends PricePersistencePortTest {

    private MemoryPersistencePort getMemoryPersistence() {
        return (MemoryPersistencePort) super.persistencePort;
    }

    @Override
    PricePersistencePort createPersistencePort() {
        return new MemoryPersistencePort();
    }

    @Override
    void persist(Price... prices) {
        for (Price p : prices) {
            getMemoryPersistence().persist(p);
        }
    }

    @Override
    void persist(Product product) {
        getMemoryPersistence().persist(product);
    }

    @Override
    void persist(Brand brand) {
        getMemoryPersistence().persist(brand);
    }

    @Override
    void cleanPersistence() {
        getMemoryPersistence().cleanUp();
    }
}