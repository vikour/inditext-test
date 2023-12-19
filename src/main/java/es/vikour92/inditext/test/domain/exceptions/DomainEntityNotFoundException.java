package es.vikour92.inditext.test.domain.exceptions;

public class DomainEntityNotFoundException extends PersistenceException {
    private static final String DEFAULT_MSG = "Could not be found entity class '%s' with ID : '%s'";
    private Class<?> domainClass;
    private Object id;
    public DomainEntityNotFoundException(String message, Class<?> domainClass, Object id, Throwable cause) {
        super(message, cause);
        this.domainClass = domainClass;
        this.id = id;
    }

    public DomainEntityNotFoundException(Class<?> domainClass, Object id) {
        this(String.format(DEFAULT_MSG, domainClass.getName(), id), domainClass, id, null);
    }
}
