package uz.devops.fido.abs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.devops.fido.abs.util.LocalDateJsonDeserializer;
import uz.devops.fido.abs.util.LocalDateJsonSerializer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Valid
    private Transmitter sender;

    @NotNull
    @Valid
    private Purpose purpose;

    @NotNull
    @Valid
    private Transmitter recipient;

    private String externalId;

    @JsonSerialize(using = LocalDateJsonSerializer.class)
    @JsonDeserialize(using = LocalDateJsonDeserializer.class)
    private LocalDate docDate;

    @NotNull
    private String type;
}
