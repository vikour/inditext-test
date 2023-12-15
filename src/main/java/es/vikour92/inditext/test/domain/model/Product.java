package es.vikour92.inditext.test.domain.model;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Product {
    @Id
    private long id;

    private String name;
}
