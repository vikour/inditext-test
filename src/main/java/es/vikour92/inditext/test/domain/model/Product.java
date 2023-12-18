package es.vikour92.inditext.test.domain.model;

import java.util.Objects;

/**
 * <p>
 *    A <code>Product</code> is sell by a <code>Brand</code> and can have multiple <code>Prices</code>. The relevance information
 *    of a product is its ID, name and Brand.
 * </p>
 * <p>
 *    For example <code></code>(1, "T-Shirt", Brand("Zara"))</code>
 *</p>
 * @param id      An <code>long</code> representing the ID
 * @param name    A not empty name.
 * @param brand   The brand of the product
 */
public record Product(long id, String name, Brand brand) {

    public Product(long id, String name, Brand brand) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Product's name cannot be null");
        this.brand = Objects.requireNonNull(brand, "Product's brand cannot be null");

        if (this.name.isEmpty()) {
            throw new IllegalArgumentException("Product's name cannot be empty");
        }
    }
}
