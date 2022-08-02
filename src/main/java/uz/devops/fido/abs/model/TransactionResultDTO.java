package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResultDTO {
    private List<CreatedTransaction> createdDocument;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatedTransaction {
        private String transactionId;
        private String externalId;
    }
}

