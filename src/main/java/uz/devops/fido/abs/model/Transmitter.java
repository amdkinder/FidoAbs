package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transmitter {

    private String pinfl;

    @Size(max = 5)
    private String codeFilial;

    private String name;

    @Size(max = 9)
    private String tax;

    @Size(max = 20)
    private String account;
}
