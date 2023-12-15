package es.vikour92.inditext.test.domain.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @Id
    private long id;
    @NotEmpty
    private String name;
}
