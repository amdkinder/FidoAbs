package uz.devops.fido.abs.model.enumuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum Currency {
    USD(840),
    RUB(643),
    ALL;


    @Getter
    private int code;

}
