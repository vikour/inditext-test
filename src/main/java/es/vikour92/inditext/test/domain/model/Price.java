package es.vikour92.inditext.test.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 *     A <code>Price</code> is the amount of money that costs a product in a specific date interval.
 * </p>
 * <p>
 *     A <code>Product</code> can have multiple prices in a date interval. In order to decide which price takes precedence its used
 *     the field <code>priority</code>.
 * </p>
 */

public class Price {

    /** Price ID **/
    private final long id;

    /** Amount of money that product costs. Must be positive or zero */
    private final BigDecimal amount;

    /** Currency's price. By default, "EUR" **/
    private final String currency;
    /** A <code>long</code> that defines the price's precedence when multiple prices intersect by date interval */
    private final long priority;
    /** The date interval in which price its available */
    private final DateInterval dateInterval;

    /** A product which affects this price */
    private final Product product;

    public static Builder builder() {
        return new Builder();
    }

    public Price(Long id, BigDecimal amount, String currency, long priority, LocalDateTime startDate, LocalDateTime endDate, Product product) {
        this.id = id;
        this.amount = validAmount(amount);
        this.currency = Objects.requireNonNullElse(currency, "EUR");
        this.product = Objects.requireNonNull(product, "A price must has a product");
        this.priority = priority;
        this.dateInterval = validInterval(startDate, endDate);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public long getPriority() {
        return priority;
    }

    public Product getProduct() {
        return product;
    }

    public LocalDateTime getStartDate() {
        return dateInterval.start();
    }

    public LocalDateTime getEndDate() {
        return dateInterval.end();
    }

    private DateInterval validInterval(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return DateInterval.of(startDate, endDate);
        }
        catch (Exception ex) {
            String msg = String.format("Start '%s' and end date '%s' aren't a valid interval", startDate, endDate);
            throw new IllegalArgumentException(msg, ex);
        }
    }

    private BigDecimal validAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be positive. Found '" + amount + "'");
        }
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return priority == price.priority &&
                Objects.equals(id, price.id) &&
                Objects.equals(amount, price.amount) &&
                Objects.equals(currency, price.currency) &&
                Objects.equals(dateInterval, price.dateInterval) &&
                Objects.equals(product, price.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currency, priority, dateInterval, product);
    }

    @Override
    public String toString() {
        return String.format("Price(product='%s', amount='%s', currency='%s', priority='%s')",
                product.name(), amount, currency, priority);
    }

    public static int compareByPriority(Price a, Price b) {
        return Long.compare(a.priority, b.priority);
    }

    public static class Builder {
        private Long id;
        private BigDecimal amount;
        private String currency;
        private long priority;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Product product;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder priority(long priority) {
            this.priority = priority;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Price build() {
            return new Price(id, amount, currency, priority, startDate, endDate, product);
        }
    }

}
