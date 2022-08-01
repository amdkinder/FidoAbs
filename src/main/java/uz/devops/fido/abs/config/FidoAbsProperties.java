package uz.devops.fido.abs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "fido-abs")
public class FidoAbsProperties {

}
