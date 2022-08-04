package uz.devops.fido.abs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.devops.fido.abs.util.LocalDateJsonDeserializer;
import uz.devops.fido.abs.util.LocalDateJsonSerializer;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    @Size(max = 5)
    private String docNum;

    private BigDecimal amount;

    private Transmitter sender;

    private Purpose purpose;

    private Transmitter recipient;

    private String externalId;

    @JsonSerialize(using = LocalDateJsonSerializer.class)
    @JsonDeserialize(using = LocalDateJsonDeserializer.class)
    private LocalDate docDate;

    private String type;
}
