package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transmitter {

    private String pinfl;

    private String codeFilial;

    private String name;

    private String tax;

    private String account;
}
