package uz.devops.fido.abs.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "fido-abs")
public class FidoAbsProperties {
    private Credentials credentials;
    @Data
    public class Credentials {
        private String username;
        private String password;
        private String token;
        private int readTimeout = 5;
        private int connectTimeout = 5;
        private String baseUri;
    }
}
