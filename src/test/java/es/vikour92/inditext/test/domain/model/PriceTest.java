package es.vikour92.inditext.test.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    public void testConstructor_thenCorrect() {
        // When all correct, none exceptions must be thrown
        assertDoesNotThrow(() -> {
            genDefaultBuilder().build();
        });
    }

    @Test
    public void testGetters_whenAllValid_thenCorrect() {
        final long id = 52L;
        final BigDecimal amount = BigDecimal.valueOf(292932L);
        final String currency = "USD";
        final long priority = 24L;
        final LocalDateTime startDate = LocalDateTime.of(2000, 6, 10, 10, 30);
        final LocalDateTime endDate = LocalDateTime.of(2000, 9, 10, 10, 30);
        final Product product = new Product(1L, "Product", new Brand(1L, "brand"));

        Price priceCreated = Price.builder()
                .id(id)
                .amount(amount)
                .currency(currency)
                .priority(priority)
                .startDate(startDate)
                .endDate(endDate)
                .product(product)
                .build();

        assertNotNull(priceCreated);
        assertAll("Getters",
                () -> assertEquals(id, priceCreated.getId()),
                () -> assertEquals(amount, priceCreated.getAmount()),
                () -> assertEquals(currency, priceCreated.getCurrency()),
                () -> assertEquals(priority, priceCreated.getPriority()),
                () -> assertEquals(startDate, priceCreated.getStartDate()),
                () -> assertEquals(endDate, priceCreated.getEndDate()),
                () -> assertEquals(product, priceCreated.getProduct())
        );
    }

    @Test
    public void testConstructor_whenNoCurrencyProvided_thenDefaultSetted() {
        Price priceCreated = genDefaultBuilder()
                .currency(null)
                .build();
        assertEquals("EUR", priceCreated.getCurrency());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.0", "0.01", "5.0", "5.5"})
    public void testAmount_whenPositiveOrZero_thenOk(String amountString) {
        BigDecimal amount = new BigDecimal(amountString);
        Price.Builder priceBuilder = genDefaultBuilder();

        assertDoesNotThrow(() -> {
            priceBuilder.amount(amount).build();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"-0.01", "-5.0", "-5.5"})
    public void testAmount_whenNegative_thenError(String amountString) {
        BigDecimal amount = new BigDecimal(amountString);
        Price.Builder priceBuilder = genDefaultBuilder();

        assertThrows(IllegalArgumentException.class, () -> {
            priceBuilder.amount(amount).build();
        }, "A exception must be thrown if the amount is negative");
    }

    @Test
    public void testConstructor_whenProductIsNull_thenError() {
        Price.Builder builder = genDefaultBuilder();
        builder.product(null);
        assertThrows(NullPointerException.class, builder::build, "When product is null should throw an exception");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2000-06-10T10:00:00.000", "2000-06-10T10:00:00.001", "2000-06-10T11:00:00.000"})
    public void testConstructor_whenDatesAreValid_thenCorrect(String paramEndDate) {
        LocalDateTime fixedStart = LocalDateTime.parse("2000-06-10T10:00:00.000");
        LocalDateTime endDate = LocalDateTime.parse(paramEndDate);
        assertDoesNotThrow(() -> {
            genDefaultBuilder()
                    .startDate(fixedStart)
                    .endDate(endDate)
                    .build();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"2000-06-10T09:59:59.999", "2000-06-10T09:00:00.001", "2000-05-10T11:00:00.000"})
    public void testConstructor_whenInvalidDate_thenError(String paramEndDate) {
        LocalDateTime fixedStart = LocalDateTime.parse("2000-06-10T10:00:00.000");
        LocalDateTime endDate = LocalDateTime.parse(paramEndDate);
        assertThrows(IllegalArgumentException.class, () -> {
            genDefaultBuilder()
                    .startDate(fixedStart)
                    .endDate(endDate)
                    .build();
        });
    }

    @Test
    public void testEqualsAndHasCode_thenOk() {
        Price.Builder priceBuilder = genDefaultBuilder();
        Price a = priceBuilder.build();
        Price b = priceBuilder.build();
        Price c = priceBuilder.build();

        // reflexive
        assertEquals(a, a);
        assertEquals(a.hashCode(), a.hashCode());

        // Symmetric
        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(b.hashCode(), a.hashCode());

        // Transitive
        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);

        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(b.hashCode(), c.hashCode());
        assertEquals(a.hashCode(), c.hashCode());
    }


    @Test
    public void testEqualsAndHashCode_whenNotEquals_thenOk() {
        Price.Builder priceBuilder = genDefaultBuilder();
        Price a = priceBuilder.amount(BigDecimal.ONE).build();
        Price b = priceBuilder.startDate(LocalDateTime.now()).build();
        Price c = priceBuilder.id(3L).build();

        assertNotEquals(a, b);
        assertNotEquals(b, c);
        assertNotEquals(a, c);

        assertNotEquals(a.hashCode(), b.hashCode());
        assertNotEquals(b.hashCode(), c.hashCode());
        assertNotEquals(a.hashCode(), c.hashCode());
    }

    @Test
    public void testToString_thenCorrect() {
        Price price = genDefaultBuilder()
                .build();
        String expectedRepresentation = "Price(product='product_name', amount='1', currency='EUR', priority='1')";
        assertEquals(expectedRepresentation, price.toString());
    }

    private Price.Builder genDefaultBuilder() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime aHourLater = now.plus(Duration.of(1, ChronoUnit.DAYS));
        Brand brand = new Brand(1L, "brand_name");
        return Price.builder()
                .amount(BigDecimal.ONE)
                .id(1L)
                .product(new Product(1L, "product_name", brand))
                .currency("EUR")
                .startDate(now)
                .endDate(aHourLater)
                .priority(1L);
    }

}