package es.vikour92.inditext.test.domain.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Price {
    @Id
    private long id;
    @Positive
    private BigDecimal amount;
    @NotEmpty
    private String currency;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Brand brand;
}
