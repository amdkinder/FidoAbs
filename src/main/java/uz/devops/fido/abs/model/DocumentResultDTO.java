package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResultDTO {
    private List<CreatedDocument> createdDocuments;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatedDocument {
        private String transactionId;
        private String externalId;
    }
}

