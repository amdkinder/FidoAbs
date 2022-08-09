package uz.devops.fido.abs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.devops.fido.abs.model.enumuration.ConversionIndicator;
import uz.devops.fido.abs.model.enumuration.PaymentType;
import uz.devops.fido.abs.util.LocalDateJsonDeserializer;
import uz.devops.fido.abs.util.LocalDateJsonSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionDTO {

    @JsonSerialize(using = ConversionIndicator.ConIndicatorSerializer.class)
    @JsonDeserialize(using = ConversionIndicator.ConIndicatorDeserializer.class)
    private ConversionIndicator indicator;

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

    @JsonSerialize(using = PaymentType.PaymentTypeSerializer.class)
    @JsonDeserialize(using = PaymentType.PaymentTypeDeSerializer.class)
    private PaymentType paymentType;

    private String codeFilial;

    private String currency;

    private String account;

    private String cardNumber;

}
