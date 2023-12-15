package es.vikour92.inditext.test.domain.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    public static final String DEFAULT_PRODUCT_NAME = "product_name";
    public static final long DEFAULT_PRODUCT_ID = 1L;

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Test
    public void testCreateBrand_thenOk() {
        Product product = new Product(DEFAULT_PRODUCT_ID, DEFAULT_PRODUCT_NAME);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testCreateBrand_whenInvalidName_thenKo(String name) {
        Product product = new Product(1L, name);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty(), "Must exists some validations");
        assertTrue(violations.stream().anyMatch(v -> "name".equals(v.getPropertyPath().toString())));
    }

    @Test
    public void testGetId_thenOk() {
        Product product = generateDefault();
        assertEquals(DEFAULT_PRODUCT_ID, product.getId());
    }

    @Test
    public void testSetId_thenOk() {
        Product product = generateDefault();
        product.setId(2L);
        assertEquals(2L, product.getId());
    }

    @Test
    public void testGetName_thenOk() {
        Product product = generateDefault();
        assertEquals(DEFAULT_PRODUCT_NAME, product.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"t-shirt", "jeans", "hat"})
    public void testSetName_whenValidName_thenOk(String name) {
        Product product = new Product(1L, "default");
        product.setName(name);
        assertEquals(name, product.getName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testSetName_whenInvalidName_thenKo(String name) {
        Product product = new Product(1L, name);

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty(), "Must exists some validations");
        assertTrue(violations.stream().anyMatch(v -> "name".equals(v.getPropertyPath().toString())));
    }

    @Test
    public void testEquals_thenOk() {
        Product a = generateDefault();
        Product b = generateDefault();
        Product c = generateDefault();

        // reflexive
        assertEquals(a, a);

        // Symmetric
        assertEquals(a, b);
        assertEquals(b, a);

        // Transitive
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);
    }


    @Test
    public void testNotEquals_thenOk() {
        Product a = new Product(1L, "a");
        Product b = new Product(2L, "b");
        Product c = new Product(3L, "c");

        assertNotEquals(a, b);
        assertNotEquals(b, c);
        assertNotEquals(a, c);
    }

    private static Product generateDefault() {
        return new Product(DEFAULT_PRODUCT_ID, DEFAULT_PRODUCT_NAME);
    }

}