package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResDTO {
    private int code;
    private String msg;
    private List<AccountDTO> responseBody;

    public AccountResDTO(List<AccountDTO> responseBody) {
        this.responseBody = responseBody;
    }
}
