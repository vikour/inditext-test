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

class BrandTest {

    public static final String DEFAULT_BRAND_NAME = "brand";
    public static final long DEFAULT_BRAND_ID = 1L;

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Test
    public void testCreateBrand_thenOk() {
        Brand brand = new Brand(DEFAULT_BRAND_ID, DEFAULT_BRAND_NAME);

        Set<ConstraintViolation<Brand>> violations = validator.validate(brand);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testCreateBrand_whenInvalidName_thenKo(String name) {
        Brand brand = new Brand(1L, name);

        Set<ConstraintViolation<Brand>> violations = validator.validate(brand);
        assertFalse(violations.isEmpty(), "Must exists some validations");
        assertTrue(violations.stream().anyMatch(v -> "name".equals(v.getPropertyPath().toString())));
    }

    @Test
    public void testGetId_thenOk() {
        Brand brand = generateDefaultBrand();
        assertEquals(DEFAULT_BRAND_ID, brand.getId());
    }

    @Test
    public void testSetId_thenOk() {
        Brand brand = generateDefaultBrand();
        brand.setId(2L);
        assertEquals(2L, brand.getId());
    }

    @Test
    public void testGetName_thenOk() {
        Brand brand = generateDefaultBrand();
        assertEquals("brand", brand.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"brand", "newBrand", "otherBrand"})
    public void testSetName_whenValidName_thenOk(String name) {
        Brand brand = new Brand(1L, "default");
        brand.setName(name);
        assertEquals(name, brand.getName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testSetName_whenInvalidName_thenKo(String name) {
        Brand brand = new Brand(1L, name);

        Set<ConstraintViolation<Brand>> violations = validator.validate(brand);
        assertFalse(violations.isEmpty(), "Must exists some validations");
        assertTrue(violations.stream().anyMatch(v -> "name".equals(v.getPropertyPath().toString())));
    }

    @Test
    public void testEquals_thenOk() {
        Brand a = generateDefaultBrand();
        Brand b = generateDefaultBrand();
        Brand c = generateDefaultBrand();

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
        Brand a = new Brand(1L, "a");
        Brand b = new Brand(2L, "b");
        Brand c = new Brand(3L, "c");

        assertNotEquals(a, b);
        assertNotEquals(b, c);
        assertNotEquals(a, c);
    }

    private static Brand generateDefaultBrand() {
        return new Brand(DEFAULT_BRAND_ID, DEFAULT_BRAND_NAME);
    }

}