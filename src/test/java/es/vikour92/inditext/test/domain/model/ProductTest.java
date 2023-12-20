package es.vikour92.inditext.test.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    public static final String DEFAULT_PRODUCT_NAME = "product_name";
    public static final long DEFAULT_PRODUCT_ID = 1L;
    public static final Brand BRAND_A = new Brand(1L, "brand a");
    public static final Brand BRAND_B = new Brand(2L, "brand b");


    @Test
    public void testCreateBrand_thenOk() {
        assertDoesNotThrow(ProductTest::generateDefault);
    }

    @Test
    public void testGetters_thenCorrect() {
        Product product = generateDefault();

        assertAll("Getters",
                () -> assertEquals(DEFAULT_PRODUCT_ID, product.id()),
                () -> assertEquals(DEFAULT_PRODUCT_NAME, product.name()),
                () -> assertEquals(BRAND_A, product.brand()));
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void testCreateBrand_whenInvalidName_thenKo(String name) {
        assertThrows(RuntimeException.class, () -> {
            new Product(DEFAULT_PRODUCT_ID, name, BRAND_A);
        });
    }


    @ParameterizedTest
    @ValueSource(strings = {"t-shirt", "jeans", "hat"})
    public void testName_whenValid_thenOk(String name) {
        assertDoesNotThrow(() -> {
            new Product(DEFAULT_PRODUCT_ID, name, BRAND_A);
        });
    }

    public void testProduct_whenNull_thenError() {
        assertThrows(NullPointerException.class, () -> {
            new Product(DEFAULT_PRODUCT_ID, DEFAULT_PRODUCT_NAME, null);
        });
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
        Product a = new Product(1L, "a", BRAND_A);
        Product b = new Product(2L, "b", BRAND_A);
        Product c = new Product(1L, "a", BRAND_B);

        assertNotEquals(a, b);
        assertNotEquals(b, c);
        assertNotEquals(a, c);
    }

    private static Product generateDefault() {
        return new Product(DEFAULT_PRODUCT_ID, DEFAULT_PRODUCT_NAME, BRAND_A);
    }

}