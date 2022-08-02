package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResDTO<T> {
    private int code;
    private String msg;
    private T responseBody;

    public AccountResDTO(T responseBody) {
        this.responseBody = responseBody;
    }
}
