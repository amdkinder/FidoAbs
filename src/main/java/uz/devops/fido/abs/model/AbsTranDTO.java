package uz.devops.fido.abs.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.devops.fido.abs.model.enumuration.AbsTranState;
import uz.devops.fido.abs.util.LocalDateJsonDeserializer;
import uz.devops.fido.abs.util.LocalDateJsonSerializer;
import uz.devops.fido.abs.util.TranStateDeserializer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AbsTranDTO {

    @Size(max = 5)
    @JsonAlias("doc_num")
    private String docNum;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Valid
    private Transmitter sender;

    @JsonDeserialize(using = TranStateDeserializer.class)
    private AbsTranState state;

    @NotNull
    @Valid
    private Purpose purpose;

    @NotNull
    @Valid
    private Transmitter recipient;

    private String externalId;

    @JsonSerialize(using = LocalDateJsonSerializer.class)
    @JsonDeserialize(using = LocalDateJsonDeserializer.class)
    @JsonAlias("doc_data")
    private LocalDate docDate;

    @NotNull
    private String type;
}
