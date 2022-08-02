package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements Serializable {
    private String condition;

    private String codeFilial;

    private String nameAcc;

    private String codeCoa;

    private String saldo;

    private String id;

    private String codeCurrency;

    private String account;
}
