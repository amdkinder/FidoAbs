package uz.devops.fido.abs.model;

import lombok.Data;

import java.io.Serializable;

@Data
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
