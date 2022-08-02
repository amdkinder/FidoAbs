package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTO {
    private String currencyCode;
    private String currencyName;
    private String rateSal;
    private String rateParch;
    private String rateCB;
}
