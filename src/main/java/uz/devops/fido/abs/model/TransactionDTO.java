package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private String docNum;

    private String amount;

    private Transmitter sender;

    private Purpose purpose;

    private Transmitter recipient;

    private String externalId;

    private LocalDate docDate;

    private String type;
}
