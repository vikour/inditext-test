package es.vikour92.inditext.test.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BrandTest {

    public static final String DEFAULT_BRAND_NAME = "brand";
    public static final long DEFAULT_BRAND_ID = 1L;

    @Test
    public void testCreateBrand_thenOk() {
        assertDoesNotThrow(BrandTest::generateDefaultBrand);
    }

    @Test
    public void testCreateBrand_whenNullName_thenKo() {
        assertThrows(NullPointerException.class, () -> {
            new Brand(1L, null);
        });
    }

    @Test
    public void testCreateBrand_whenEmptyName_thenKo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Brand(1L, "");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"brand", "newBrand", "otherBrand"})
    public void testSetName_whenValidName_thenOk(String name) {
        Brand brand = new Brand(1L, name);
        assertEquals(name, brand.name());
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