package uz.devops.fido.abs.model;

public interface BaseResultDTO {
    Boolean getSuccess();

    String getMsg();

    int getCode();

    Exception getException();
}
