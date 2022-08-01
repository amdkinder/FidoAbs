package uz.devops.fido.abs.model;

import lombok.Data;

import java.util.List;

@Data
public class DocumentResultDTO {
    private List<CreatedDocument> createdDocuments;

    @Data
    public static class CreatedDocument {
        private String transactionId;
        private String externalId;
    }
}

