package es.vikour92.inditext.test.domain.exceptions;

public class DomainEntityNotFoundException extends PersistenceException {
    private static final String DEFAULT_MSG = "Could not be found entity class '%s' with ID : '%s'";
    private final Class<?> domainClass;
    private final Object domainId;
    public DomainEntityNotFoundException(String message, Class<?> domainClass, Object domainId, Throwable cause) {
        super(message, cause);
        this.domainClass = domainClass;
        this.domainId = domainId;
    }

    public DomainEntityNotFoundException(Class<?> domainClass, Object domainId) {
        this(String.format(DEFAULT_MSG, domainClass.getName(), domainId), domainClass, domainId, null);
    }

    public Class<?> getDomainClass() {
        return domainClass;
    }

    public Object getDomainId() {
        return domainId;
    }
}
