package uz.devops.fido.abs.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExchangeRateCriteria {
    private LocalDate dateCross;
    private String currencyCode;
}
