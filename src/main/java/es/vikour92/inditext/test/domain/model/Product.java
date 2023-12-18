package es.vikour92.inditext.test.domain.model;

import java.util.Objects;

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
