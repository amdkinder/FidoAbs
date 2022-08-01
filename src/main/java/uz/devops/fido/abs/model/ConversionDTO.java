package uz.devops.fido.abs.model;

import lombok.Data;

@Data
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

    private String convertionDateTime;

    private String rollbackIs;

    private String paymentType;

    private String codeFilial;

    private String currency;

    private String account;

    private String cardNumber;

}
