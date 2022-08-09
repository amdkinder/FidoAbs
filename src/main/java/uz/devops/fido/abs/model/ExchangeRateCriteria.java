package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.devops.fido.abs.model.enumuration.Currency;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateCriteria {
    private LocalDate dateCross;
    private Currency currencyCode;
}
