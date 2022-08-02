package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionDTO {

    private String indicator;

    private String amount;

    private String clientId;

    private String con;

    private String purpose;

    private String buy;

    private String externalCardNumber;

    private String dateExp;

    private String isMasked;

    private LocalDate convertionDateTime;

    private String rollbackIs;

    private String paymentType;

    private String codeFilial;

    private String currency;

    private String account;

    private String cardNumber;

}
