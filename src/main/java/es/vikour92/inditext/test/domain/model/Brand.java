package es.vikour92.inditext.test.domain.model;

import java.util.Objects;

public record Brand(long id, String name) {

    public Brand(long id, String name) {
        Objects.requireNonNull(name, "Name's brand cannot be null");

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name's brand cannot be empty");
        }

        this.id = id;
        this.name = name;
    }
}
