package uz.devops.fido.abs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.devops.fido.abs.util.LocalDateJsonDeserializer;
import uz.devops.fido.abs.util.LocalDateJsonSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionDTO {

    private String indicator;

    private BigDecimal amount;

    private String clientId;

    private String con;

    private String purpose;

    private String buy;

    private String externalCardNumber;

    private String dateExp;

    private String isMasked;

    @JsonDeserialize(using = LocalDateJsonDeserializer.class)
    @JsonSerialize(using = LocalDateJsonSerializer.class)
    private LocalDate convertionDateTime;

    private String rollbackIs;

    private String paymentType;

    private String codeFilial;

    private String currency;

    private String account;

    private String cardNumber;

}
