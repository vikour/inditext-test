package es.vikour92.inditext.test.domain.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Brand {
    @Id
    private Long id;

    @NotEmpty
    private String name;

}
