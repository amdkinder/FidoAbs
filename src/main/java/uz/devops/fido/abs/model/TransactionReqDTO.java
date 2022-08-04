package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReqDTO {
    private List<TransactionDTO> documents;

    public TransactionReqDTO(TransactionDTO ...transactionDTOs) {
        this.documents = Arrays.asList(transactionDTOs);
    }
}
