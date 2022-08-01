package uz.devops.fido.abs.model;

import lombok.Data;

@Data
public class DocumentDTO {

    private String docNum;

    private String amount;

    private Transmitter sender;

    private Purpose purpose;

    private Transmitter recipient;

    private String externalId;

    private String docDate;

    private String type;
}
